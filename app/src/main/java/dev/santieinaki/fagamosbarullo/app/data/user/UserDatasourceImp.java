package dev.santieinaki.fagamosbarullo.app.data.user;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.datasource.UserDatasource;

public class UserDatasourceImp implements UserDatasource {

    @Override
    public boolean register(User user) {

        Map<String, Object> userMap = new HashMap<>();

        userMap.put("fName", user.getFullName());
        userMap.put("email", user.getEmail());
        userMap.put("phone", user.getPhoneNumber());
        userMap.put("followers", new ArrayList<String>());
        userMap.put("following", new ArrayList<String>());
        userMap.put("fotos", new ArrayList<String>());
        userMap.put("instrumento", new ArrayList<String>());
        userMap.put("titulos", new ArrayList<String>());
        userMap.put("maquetas", new ArrayList<String>());
        userMap.put("titulos2", new ArrayList<String>());
        userMap.put("caratulas", new ArrayList<String>());
        userMap.put("descripcion", "");

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users");
        DocumentReference documentReference = collectionReference.document(user.getId());
        Task<Void> task = documentReference.set(userMap);

        try {
            Tasks.await(task);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return task.isSuccessful();
    }

    @Override
    public User getUserInfoWithEmail(String email) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users");
        Task<QuerySnapshot> task = collectionReference.whereEqualTo("email", email).get();

        try {
            Tasks.await(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if (!task.isSuccessful()) {
            return null;
        }

        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
        String name = documentSnapshot.getString("fName");
        String phone = documentSnapshot.getString("phone");
        List<String> followers = (List<String>) documentSnapshot.get("followers");
        List<String> following = (List<String>) documentSnapshot.get("following");
        List<String> instrumento = (List<String>) documentSnapshot.get("instrumento");
        List<String> fotos = (List<String>) documentSnapshot.get("fotos");
        List<String> titulos = (List<String>) documentSnapshot.get("titulos");
        List<String> maquetas = (List<String>) documentSnapshot.get("maquetas");
        List<String> titulos2 = (List<String>) documentSnapshot.get("titulos2");
        List<String> caratulas = (List<String>) documentSnapshot.get("caratulas");
        String descripcion = documentSnapshot.getString("descripcion");

        User user = new User(name, email, phone, instrumento, descripcion, documentSnapshot.getId());
        user.setFollowers(followers);
        user.setFollowing(following);
        user.setFotos(fotos);
        user.setTitulos(titulos);
        user.setMaquetas(maquetas);
        user.setTitulosMaquetas(titulos2);
        user.setCaratulas(caratulas);

        return user;
    }

    @Override
    public List<User> searchUsers(String name,
                                  List<String> instrumentos,
                                  boolean favs) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users");
        Task<QuerySnapshot> task = collectionReference.get();

        try {
            Tasks.await(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if (!task.isSuccessful()) {
            return null;
        }

        List<User> users = new ArrayList<>();
        QuerySnapshot querySnapshot = task.getResult();
        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
            String fName = doc.getString("fName");
            String email = doc.getString("email");
            String phone = doc.getString("phone");
            List<String> followers = (List<String>) doc.get("followers");
            List<String> following = (List<String>) doc.get("following");
            List<String> instrumento = (List<String>) doc.get("instrumento");
            List<String> fotos = (List<String>) doc.get("fotos");
            List<String> titulos = (List<String>) doc.get("titulos");
            List<String> maquetas = (List<String>) doc.get("maquetas");
            List<String> titulos2 = (List<String>) doc.get("titulos2");
            List<String> caratulas = (List<String>) doc.get("caratulas");
            String descripcion = doc.getString("descripcion");

            if (fName.toLowerCase().indexOf(name.toLowerCase()) != -1 && checkInstrumentos(instrumentos,
                    instrumento) && checkFavs(favs, followers)) {
                User user = new User(fName, email, phone, instrumento, descripcion, doc.getId());
                user.setFollowing(following);
                user.setFollowers(followers);
                user.setFotos(fotos);
                user.setTitulos(titulos);
                user.setMaquetas(maquetas);
                user.setTitulosMaquetas(titulos2);
                user.setCaratulas(caratulas);

                users.add(user);
            }
        }

        return users;
    }

    @Override
    public User follow(User user,
                       String id) {

        User myUser = getUserInfo(id);

        // do i follow him?
        int idx = user.getFollowers().indexOf(id);
        if (idx != -1) {

            user.getFollowers().remove(idx);
            myUser.getFollowing().remove(user.getId());
        } else {

            user.getFollowers().add(id);
            myUser.getFollowing().add(user.getId());
        }

        // save info
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("fName", user.getFullName());
        userMap.put("email", user.getEmail());
        userMap.put("phone", user.getPhoneNumber());
        userMap.put("followers", user.getFollowers());
        userMap.put("following", user.getFollowing());
        userMap.put("fotos", user.getFotos());
        userMap.put("titulos", user.getTitulos());
        userMap.put("instrumento", user.getInstrumento());
        userMap.put("descripcion", user.getDescripcion());
        userMap.put("maquetas", user.getMaquetas());
        userMap.put("titulos2", user.getTitulosMaquetas());
        userMap.put("caratulas", user.getCaratulas());

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users");
        DocumentReference documentReference = collectionReference.document(user.getId());
        Task<Void> task = documentReference.update(userMap);

        try {
            Tasks.await(task);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        saveUserInfo(myUser);

        return user;
    }

    @Override
    public User uploadMaqueta(User user,
                          Uri uri,
                          Uri caratula,
                          String titulo) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = storageReference
                .child("users/" + user.getEmail() + "/maquetas/maqueta" + user.getMaquetas().size());
        StorageReference fileRef2 = storageReference
                .child("users/" + user.getEmail() + "/maquetas/caratula" + user.getMaquetas().size());

        UploadTask task = fileRef.putFile(uri);
        UploadTask task2 = fileRef2.putFile(caratula);
        try {
            Tasks.await(task);
            Tasks.await(task2);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        if (!task.isSuccessful() || !task2.isSuccessful()) {
            return null;
        }

        Task<Uri> taskUri = fileRef.getDownloadUrl();
        Task<Uri> taskUri2 = fileRef2.getDownloadUrl();
        try {

            Tasks.await(taskUri);
            Tasks.await(taskUri2);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        if (!taskUri.isSuccessful() || !taskUri2.isSuccessful()) {
            return null;
        }

        user.getMaquetas().add(taskUri.getResult().toString());
        user.getTitulosMaquetas().add(titulo);
        user.getCaratulas().add(taskUri2.getResult().toString());
        saveUserInfo(user);

        return getUserInfo(user.getId());
    }

    @Override
    public User uploadPhoto(User user,
                            Uri uri,
                            String titulo) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = storageReference
                .child("users/" + user.getEmail() + "/photos/" + user.getFotos().size());
        UploadTask task = fileRef.putFile(uri);
        Task<Uri> taskUri;

        try {
            Tasks.await(task);
            if (!task.isSuccessful()) {
                return null;
            }
            taskUri = fileRef.getDownloadUrl();
            Tasks.await(taskUri);
            if (!taskUri.isSuccessful()) {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        user.getFotos().add(taskUri.getResult().toString());
        user.getTitulos().add(titulo);
        saveUserInfo(user);

        return getUserInfo(user.getId());
    }

    @Override
    public User getUserInfo(String id) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users");
        DocumentReference documentReference = collectionReference.document(id);
        Task<DocumentSnapshot> task = documentReference.get();

        try {
            Tasks.await(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        if (!task.isSuccessful()) {
            return null;
        }

        DocumentSnapshot documentSnapshot = task.getResult();
        String name = documentSnapshot.getString("fName");
        String email = documentSnapshot.getString("email");
        String phone = documentSnapshot.getString("phone");
        List<String> followers = (List<String>) documentSnapshot.get("followers");
        List<String> following = (List<String>) documentSnapshot.get("following");
        List<String> instrumento = (List<String>) documentSnapshot.get("instrumento");
        List<String> fotos = (List<String>) documentSnapshot.get("fotos");
        List<String> titulos = (List<String>) documentSnapshot.get("titulos");
        List<String> maquetas = (List<String>) documentSnapshot.get("maquetas");
        List<String> titulos2 = (List<String>) documentSnapshot.get("titulos2");
        List<String> caratulas = (List<String>) documentSnapshot.get("caratulas");
        String descripcion = documentSnapshot.getString("descripcion");

        User user = new User(name, email, phone, instrumento, descripcion, documentSnapshot.getId());
        user.setFollowers(followers);
        user.setFollowing(following);
        user.setFotos(fotos);
        user.setTitulos(titulos);
        user.setMaquetas(maquetas);
        user.setTitulosMaquetas(titulos2);
        user.setCaratulas(caratulas);

        return user;
    }

    @Override
    public void saveUserInfo(User user) {

        Map<String, Object> userMap = new HashMap<>();

        userMap.put("fName", user.getFullName());
        userMap.put("email", user.getEmail());
        userMap.put("phone", user.getPhoneNumber());
        userMap.put("followers", user.getFollowers());
        userMap.put("following", user.getFollowing());
        userMap.put("instrumento", user.getInstrumento());
        userMap.put("titulos", user.getTitulos());
        userMap.put("fotos", user.getFotos());
        userMap.put("descripcion", user.getDescripcion());
        userMap.put("maquetas", user.getMaquetas());
        userMap.put("titulos2", user.getTitulosMaquetas());
        userMap.put("caratulas", user.getCaratulas());

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users");
        DocumentReference documentReference = collectionReference.document(user.getId());
        Task<Void> task = documentReference.update(userMap);

        try {
            Tasks.await(task);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return;
    }

    // Aux methods
    private boolean checkInstrumentos(List<String> haber,
                                      List<String> tiene) {

        int count = 0;
        for (String instrumento : tiene) {

            if (haber.indexOf(instrumento) != -1) {
                count++;
            }
        }

        return count == haber.size();
    }

    private boolean checkFavs(boolean favs,
                              List<String> followers) {

        if (!favs) {

            return true;
        }

        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return followers.indexOf(myId) != -1;
    }
}
