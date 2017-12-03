package in.ac.kuexam.kudatesheets;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dipanker on 24/07/17.
 */

public class adeptor extends RecyclerView.Adapter<adeptor.MyViewHolder> {
    Context context;
    ArrayList<data> arrayList;
    public adeptor(ArrayList<data> arrayList, Context context){
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public adeptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view,arrayList,context);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(adeptor.MyViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ArrayList<data> arrayList;
        Context context;
        TextView textView;

        public MyViewHolder(View itemView, ArrayList<data> arrayList, Context context) {
            super(itemView);
            this.arrayList=arrayList;
            this.context=context;
            itemView.setOnClickListener(this);
            textView=(TextView) itemView.findViewById(R.id.item);
        }

        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(arrayList.get(pos).getUrl()));
            request.setTitle(arrayList.get(pos).getName());
            request.setDescription("Downloading...");

            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,arrayList.get(pos).getName()+".pdf");
            DownloadManager manager=(DownloadManager)v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);

        }
    }

}
