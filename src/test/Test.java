package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Servlet implementation class Test
 */

public class Test extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            file = new FileInputStream("D:\\Downloads\\nodal-clock-191221-firebase-adminsdk-hgprd-991400b24c.json");
        } catch (FileNotFoundException e1) {
        }
        try {
            options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(file))
                    .setDatabaseUrl("https://nodal-clock-191221.firebaseio.com/").build();
        } catch (IOException e) {
        }
        if(options!=null||app!=null) {
            app=FirebaseApp.initializeApp(options);
            database=FirebaseDatabase.getInstance(app);
            databaseReference=database.getReference("chat");
        }
    }
    DatabaseReference databaseReference;
    FileInputStream file=null;
    FirebaseOptions options=null;
    FirebaseApp app=null;
    FirebaseDatabase database=null;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        PrintWriter writer=response.getWriter();
        writer.write("hello world");
        //databaseReference.push().setValue(new Chat("hello from ide","web"),null);
        databaseReference.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                // TODO Auto-generated method stub
                Chat post = snapshot.getValue(Chat.class);
                writer.println(post.name.toString()+"\n"+post.text.toString());
                System.out.println(post.name.toString()+"\n"+post.text.toString());
            }


            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
                // TODO Auto-generated method stub

            }


            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                // TODO Auto-generated method stub

            }


            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
                // TODO Auto-generated method stub

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // TODO Auto-generated method stub

            }
        });
        System.out.println("hey");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
    public static class Chat implements Serializable {
        public String text;
        public String name;

        Chat(String a, String b) {
            text = a;
            name = b;
        }

        Chat() {

        }

    }

}
