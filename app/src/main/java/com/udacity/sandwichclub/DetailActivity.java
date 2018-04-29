package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    Sandwich currentSandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        currentSandwich = sandwich;
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //Get the TextViews
        TextView mDescriptionTv = findViewById(R.id.description_tv);
        TextView mIngredientsTv = findViewById(R.id.ingredients_tv);
        TextView mOriginTv = findViewById(R.id.origin_tv);
        TextView mAlsoKnownTv = findViewById(R.id.also_known_tv);



        //Set the textviews

        //Set description text
        mDescriptionTv.setText(currentSandwich.getDescription());

        //Set ingredient text
        for(int i = 0; i < currentSandwich.getIngredients().size(); i++){
            if(i != 0) mIngredientsTv.append("\n");
            if(currentSandwich.getIngredients().size() > 1) mIngredientsTv.append("\u25CF ");
            mIngredientsTv.append(currentSandwich.getIngredients().get(i));
        }

        //Set origin text
        mOriginTv.setText(currentSandwich.getPlaceOfOrigin());

        //Set alsoKnownAs text
        for(int i = 0; i < currentSandwich.getAlsoKnownAs().size(); i++){
            if(i != 0) mAlsoKnownTv.append("\n");
            if(currentSandwich.getAlsoKnownAs().size() > 1) mAlsoKnownTv.append("\u25CF ");
            mAlsoKnownTv.append(currentSandwich.getAlsoKnownAs().get(i));
        }
    }
}
