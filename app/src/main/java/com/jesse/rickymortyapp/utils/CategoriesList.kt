package com.jesse.rickymortyapp.utils

object CategoriesList {
    val categories = listOf("All", "Alive", "Dead")
    //This variable is for testing purposes. This is to assert that in each time
    //a new category is selected the OverviewViewModel currentItemPosition is
    //updated and the category state is saved.
    var currentCategoryItemPosition = 0
}