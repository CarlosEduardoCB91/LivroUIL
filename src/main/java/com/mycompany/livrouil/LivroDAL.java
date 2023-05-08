/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.livrouil;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Precondition;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos Eduardo
 */
public class LivroDAL {

    CollectionReference reference;

    static Firestore db;

    public static boolean guardaLivro(String collection, String documento, Map<String, Object> data) {
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(collection).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Salvo com Sucesso");
            return true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static boolean atualizarLivro(String collection, String documento, Map<String, Object> data) {
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(collection).document(documento);
            ApiFuture<WriteResult> result = docRef.update(data);
            System.out.println("Atualizado com Sucesso");
            return true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static boolean deletarLivro(String collection, String documento) {
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(collection).document(documento);
            ApiFuture<WriteResult> result = docRef.delete(Precondition.NONE);
            System.out.println("Deletado com Sucesso");
            return true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public static void carregarTabelaLivro(JTable table) {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Titulo");
        model.addColumn("Autor");
        model.addColumn("Editora");
        model.addColumn("Ano");
        model.addColumn("Localizacao");

        try {
            CollectionReference livro = Conexao.db.collection("livro");
            ApiFuture<QuerySnapshot> querySnap = livro.get();

            for (DocumentSnapshot document : querySnap.get().getDocuments()) {
                model.addRow(new Object[]{
                    document.getString("titulo"),
                    document.getString("autor"),
                    document.getString("editora"),
                    document.getString("ano"),
                    document.getString("localizacao")
                });
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error: " + e.getMessage());
        }
        table.setModel(model);
    }

}
