package com.minwein.customer.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.minwein.customer.R;
import com.minwein.customer.activity.MenuDetailActivity;
import com.minwein.customer.api_model.ItemDetailsResponse;
import com.minwein.customer.model.Ingredients;
import com.minwein.customer.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMSHEER on 25-01-2018.
 */

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.MyViewHolder> {
    private final Context context;
    private final List<ItemDetailsResponse.Ingredient> ingredients;
    private final List<Ingredients> selectedIngredientsList;
    private final String groupName;
    private final String groupKey;
    private final Integer minimum;
    private final Integer maximum;
    private final ArrayList<Object> ingredientsId;
    private final Integer item_id;

    public IngredientsListAdapter(Context context, List<ItemDetailsResponse.Ingredient> ingredients,
                                  List<Ingredients> ingredientsList, String item_ingredient_group_key,
                                  String group_name, Integer minimum, Integer maximum, Integer item_id) {
        this.context = context;
        this.ingredients = ingredients;
        this.selectedIngredientsList = ingredientsList;
        this.groupKey = item_ingredient_group_key;
        this.groupName = group_name;
        this.minimum = minimum;
        this.maximum = maximum;
        ingredientsId  =  new ArrayList<>();
        this.item_id = item_id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ingredient_list, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ItemDetailsResponse.Ingredient ingredient = ingredients.get(position);
        if ( ingredient.getDetails() != null) {
            holder.cbIngredient.setText(ingredient.getDetails().getIngredient_name());
            holder.tvIngredientPrice.setText(SessionManager.getInstance().getCurrencySymbol()+" "+ingredient.getPrice());
        }else{
            holder.cbIngredient.setText("");
        }
        if (SessionManager.getInstance().getAppLanguage().equals("ar")){
            holder.tvIngredientPrice.setGravity(Gravity.START);
        }else {
            holder.tvIngredientPrice.setGravity(Gravity.END);
        }
//        if (ingredients.contains(ingredient))

        holder.cbIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemDetailsResponse.Ingredient ingredients_list = ingredient;
                if (ingredients_list.getDetails() != null) {
                    if (minimum > 0) {
                        if (ingredientsId.size() < maximum
                                || ingredientsId.contains(groupKey)) {

                            ArrayList<String> selectedIngId = new ArrayList<String>();
                            for (int count = 0; count < MenuDetailActivity.ingredientsModels.size(); count++) {
                                selectedIngId.add(MenuDetailActivity.ingredientsModels.get(count).getIngredientsId());
                            }

                            if (selectedIngId.contains(ingredients_list.getDetails().getIngredient_key())) {
                                for (int count = 0; count < selectedIngId.size(); count++) {
                                    if (selectedIngId.get(count).equals(ingredients_list.getDetails().getIngredient_key())) {
                                        MenuDetailActivity.ingredientsModels.remove(count);
                                        ingredientsId.remove(ingredients_list.getDetails().getIngredient_key());
                                    }
                                }
                            } else {
                                Ingredients ingredients = new Ingredients();
                                ingredients.setPrice(ingredients_list.getPrice().toString());
                                ingredients.setIngredientsQuantity("1");
                                ingredients.setIngredientsId(ingredients_list.getDetails().getIngredient_key());
                                ingredients.setIngredientsKey(ingredients_list.getDetails().getIngredient_key());
                                ingredients.setIngredient_name(ingredients_list.getDetails().getIngredient_name());
                                ingredients.setItem_ingredients_id(item_id);
                                ingredients.setGroupKey(groupKey);

                                MenuDetailActivity.ingredientsModels.add(ingredients);
                                ingredientsId.add(ingredients_list.getDetails().getIngredient_key());

                            }


                        } else {
                            Toast.makeText(context, "Maximum ingredients reached", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        if (MenuDetailActivity.ingredientsModels.size() == 0) {
                            Ingredients ingredientsModel = new Ingredients();
                            ingredientsModel.setIngredient_name(ingredients_list.getDetails().getIngredient_name());
                            ingredientsModel.setIngredientsId(ingredients_list.getDetails().getIngredient_key());
                            ingredientsModel.setIngredientsKey(ingredients_list.getDetails().getIngredient_key());
                            ingredientsModel.setPrice(ingredients_list.getPrice().toString());
                            ingredientsModel.setIngredientsQuantity("1");
                            ingredientsModel.setItem_ingredients_id(item_id);
                            ingredientsModel.setGroupKey(groupKey);
                            MenuDetailActivity.ingredientsModels.add(ingredientsModel);

                            ingredientsId.add(ingredients_list.getDetails().getIngredient_key());
                        } else {

                            if (ingredientsId.size() < maximum
                                    || ingredientsId.contains(ingredients_list.getDetails().getIngredient_key())) {
                                ArrayList<String> selectedIngId = new ArrayList<String>();
                                for (int count = 0; count < MenuDetailActivity.ingredientsModels.size(); count++) {
                                    selectedIngId.add(MenuDetailActivity.ingredientsModels.get(count).getIngredientsId());
                                }

                                if (selectedIngId.contains(ingredients_list.getDetails().getIngredient_key())) {
                                    for (int count = 0; count < selectedIngId.size(); count++) {
                                        if (selectedIngId.get(count).equals(ingredients_list.getDetails().getIngredient_key())) {
                                            MenuDetailActivity.ingredientsModels.remove(count);
                                            ingredientsId.remove(ingredients_list.getDetails().getIngredient_key());
                                        }
                                    }
                                } else {
                                    Ingredients ingredients = new Ingredients();
                                    ingredients.setPrice(ingredients_list.getPrice().toString());
                                    ingredients.setIngredientsQuantity("1");
                                    ingredients.setIngredientsId(ingredients_list.getDetails().getIngredient_key());
                                    ingredients.setIngredientsKey(ingredients_list.getDetails().getIngredient_key());
                                    ingredients.setIngredient_name(ingredients_list.getDetails().getIngredient_name());
                                    ingredients.setItem_ingredients_id(item_id);
                                    ingredients.setGroupKey(groupKey);
                                    MenuDetailActivity.ingredientsModels.add(ingredients);
                                    ingredientsId.add(ingredients_list.getDetails().getIngredient_key());

                                }


                            } else {
                                Toast.makeText(context, "Maximum ingredients reached", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }else{
                    holder.cbIngredient.setChecked(false);
                    Toast.makeText(context, "Wrong Ingredients mapping", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbIngredient;
        TextView tvIngredientPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            cbIngredient = (CheckBox) itemView.findViewById(R.id.cbIngredient);
            tvIngredientPrice = (TextView) itemView.findViewById(R.id.tvIngredientPrice);
        }
    }
}
