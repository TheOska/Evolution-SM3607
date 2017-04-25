package kaihcheng7_kmlee65.scm.evolution.modelgame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kaihcheng7_kmlee65.scm.evolution.R;
import kaihcheng7_kmlee65.scm.evolution.modelgame.model.CraftItem;


public class BottomItemAdapter extends RecyclerView.Adapter<BottomItemAdapter.MyViewHolder> {
    private ArrayList<Integer> imageData;
    private ArrayList<String> textData;
    private ArrayList<CraftItem> craftItemsData;
    private Context context;

    public BottomItemAdapter(ArrayList<Integer> imageData, ArrayList<String> textData, Context context){
        this.imageData = imageData;
        this.textData = textData;
        this.context = context;
    }
    public BottomItemAdapter(ArrayList<CraftItem> craftItemsData, Context context){
        this.craftItemsData = craftItemsData;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemText;
        public MyViewHolder(View view){
            super(view);
            itemImage = (ImageView) view.findViewById(R.id.bottom_item_image);
            itemText = (TextView) view.findViewById(R.id.bottom_item_text);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bottom_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.itemText.setText(textData.get(position));
        holder.itemText.setText(craftItemsData.get(position).getCraftName());
//        holder.itemImage.setImageResource(craftItemsData.get(position).getCraftImage());
        holder.itemImage.setBackground(craftItemsData.get(position).getCraftDrawable());
    }

    @Override
    public int getItemCount() {
        return craftItemsData.size();
    }


}
