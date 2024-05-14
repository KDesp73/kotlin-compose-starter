package io.github.kdesp73.myapplication.backend.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FirestoreManager {
    private val TAG = "FirestoreManager"
    private val db = FirebaseFirestore.getInstance()

    suspend fun getDocument(path: String): Map<String, Any>? {
        return try {
            val documentSnapshot = db.document(path).get().await()
            documentSnapshot.data
        } catch (e: Exception) {
            null
        }
    }

    // Function to add a new document to a collection
    suspend fun addDocument(collectionPath: String, data: Map<String, Any>): String? {
        return try {
            val documentReference = db.collection(collectionPath).add(data).await()
            documentReference.id
        } catch (e: Exception) {
            null
        }
    }

    // Function to update a document
    suspend fun updateDocument(path: String, data: Map<String, Any>): Boolean {
        return try {
            db.document(path).update(data).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Function to delete a document
    suspend fun deleteDocument(path: String): Boolean {
        return try {
            db.document(path).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Function to query documents in a collection
    suspend fun queryCollection(collectionPath: String): List<Map<String, Any>>? {
        return try {
            val querySnapshot = db.collection(collectionPath).get().await()
            parseQuerySnapshot(querySnapshot)
        } catch (e: Exception) {
            null
        }
    }

    // Helper function to parse QuerySnapshot into a list of documents
    private fun parseQuerySnapshot(querySnapshot: QuerySnapshot): List<Map<String, Any>> {
        val documents = mutableListOf<Map<String, Any>>()
        for (document in querySnapshot.documents) {
            document.data?.let { documents.add(it) }
        }
        return documents
    }
}