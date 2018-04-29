package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.MainActivity;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //parseSandwichJson function returns a new Sandwich object with data parsed from JSON
    public static Sandwich parseSandwichJson(String json) {

        //Create sandwich property variables
        String mainName = null;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();

        //Try to get and set the properties from the JSON
        try{
            //Create a JSONObject from the json String
            JSONObject sandwichJson = new JSONObject(json);

            //Set mainName
            mainName = sandwichJson.getJSONObject("name").getString("mainName");

            //Get the alsoKnownAs property, then convert it into a List<String>, and set the alsoKnownAs variable
            JSONArray tempAlsoKnownAs = sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs");
            alsoKnownAs = jsonArrayToStringList(tempAlsoKnownAs);

            //Set placeOfOrigin
            placeOfOrigin = sandwichJson.getString("placeOfOrigin");

            //Set description
            description = sandwichJson.getString("description");

            //Set image
            image = sandwichJson.getString("image");

            //Get the ingredients property, then convert it into a List<String>, and set the alsoKnownAs variable
            JSONArray tempIngredients = sandwichJson.getJSONArray("ingredients");
            ingredients = jsonArrayToStringList(tempIngredients);

        } catch (JSONException e) {
            //If there's a problem, catch and log an error message
            Log.e(MainActivity.class.getName(), "Error parsing JSON! : " + e.toString());
            e.printStackTrace();
        }

        //Create a new Sandwich variable with the retrieved attributes
        Sandwich returnSandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        //Return the returnSandwich variable
        return returnSandwich;
    }

    //Function converts a JSONArray into a List<String>
    private static List<String> jsonArrayToStringList(JSONArray json){
        //Create the list we'll be returning
        List<String> returnList = new ArrayList<>();
        try{
            //For each list item, add that item to the List
            for(int i = 0; i < json.length(); i++){
                returnList.add(json.getString(i));
            }
        } catch (JSONException e) {
            //If there's a problem, catch and log an error message
            Log.e(MainActivity.class.getName(), "Error parsing JSON! : " + e.toString());
            e.printStackTrace();
        }
        return returnList;
    }
}
