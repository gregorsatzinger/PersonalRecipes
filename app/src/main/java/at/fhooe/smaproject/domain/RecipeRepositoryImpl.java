package at.fhooe.smaproject.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;

import at.fhooe.smaproject.dal.*;
import at.fhooe.smaproject.models.Category;
import at.fhooe.smaproject.models.Recipe;

public class RecipeRepositoryImpl implements RecipeRepository {

    private final CategoryDao categoryDao;
    private final DescriptionImageDao descriptionImageDao;
    private final RecipeCategoryDao recipeCategoryDao;
    private final RecipeDao recipeDao;

    //region helper methods
    private Recipe toDomainRecipe(DbRecipe dbRecipe) {
        // find all recipe-category relations
        Collection<DbRecipeCategory> recipeCategoryRelations
                = recipeCategoryDao.findByRecipeId(dbRecipe.getId());

        // find every DB-category in relation to recipe
        // transform DB-category to domain-category and add to collection
        ArrayList<Category> categoriesOfRecipe = new ArrayList<>();
        for (DbRecipeCategory rel : recipeCategoryRelations) {
            DbCategory dbCategory = categoryDao.findById(rel.getCategoryId());
            categoriesOfRecipe.add(new Category(
                    dbCategory.getId(),
                    dbCategory.getName()
            ));
        }

        Collection<DbDescriptionImage> descriptionImages = descriptionImageDao.findByRecipeId(dbRecipe.getId());
        ArrayList<String> descriptionImagePaths = new ArrayList<>();
        for(DbDescriptionImage db : descriptionImages) {
            descriptionImagePaths.add(db.getImagePath());
        }

        return new Recipe(
                dbRecipe.getId(),
                dbRecipe.getTitle(),
                dbRecipe.getTitleimage(),
                dbRecipe.getDescription(),
                dbRecipe.getComment(),
                dbRecipe.getRating(),
                categoriesOfRecipe,
                descriptionImagePaths
        );
    }

    private DbRecipe toDbRecipe(Recipe recipe) {
        return new DbRecipe(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getTitleImage(),
                recipe.getDescription(),
                recipe.getComment(),
                recipe.getRating()
        );
    }

    private void insertCategoriesForRecipe(Recipe recipe) {
        for(Category category : recipe.getCategories()) {
            recipeCategoryDao.insert(new DbRecipeCategory(
                    recipe.getId(), category.getId()
            ));
        }
    }

    private void deleteCategoriesForRecipe(Recipe recipe) {
        Collection<DbRecipeCategory> existingCategoryRelations
                = recipeCategoryDao.findByRecipeId(recipe.getId());

        for(DbRecipeCategory relation : existingCategoryRelations) {
            recipeCategoryDao.delete(relation.getRecipeId(), relation.getCategoryId());
        }
    }

    private void insertDescriptionImagesForRecipe(Recipe recipe) {
        ArrayList<String> imagePaths = recipe.getDescriptionImagePaths();
        for (int i = 0; i < imagePaths.size(); i++) {
            descriptionImageDao.insert(new DbDescriptionImage(
                    -1, //is generated
                    imagePaths.get(i),
                    i+1,
                    recipe.getId()
            ));
        }
    }

    private void deleteDescriptionImagesForRecipe(Recipe recipe) {
        Collection<DbDescriptionImage> existingDescriptionImages =
                descriptionImageDao.findByRecipeId(recipe.getId());

        for(DbDescriptionImage image : existingDescriptionImages) {
            descriptionImageDao.delete(image.getId());
        }
    }
    //endregion

    public RecipeRepositoryImpl(Context context) {
        categoryDao = new CategoryDao(context);
        descriptionImageDao = new DescriptionImageDao(context);
        recipeCategoryDao = new RecipeCategoryDao(context);
        recipeDao = new RecipeDao(context);
    }

    @Override
    public Collection<Recipe> findAllRecipes() {
        Collection<DbRecipe> dbRecipes = recipeDao.findAll();
        Collection<Recipe> recipes = new ArrayList<>();
        for(DbRecipe dbRecipe : dbRecipes) {
            recipes.add(toDomainRecipe(dbRecipe));
        }
        return recipes;
    }

    @Override
    public Recipe findRecipeById(int recipeId) {
        // find recipe DB-entity
        DbRecipe dbRecipe = recipeDao.findById(recipeId);
        if(dbRecipe == null) return null;

        return toDomainRecipe(dbRecipe);
    }

    @Override
    public int createRecipe(Recipe recipe) {
        // insert recipe
        int generatedId = recipeDao.insert(toDbRecipe(recipe));
        recipe.setId(generatedId);

        // insert category relations
        insertCategoriesForRecipe(recipe);

        // insert description images
        insertDescriptionImagesForRecipe(recipe);

        return generatedId;
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        // update recipe
        recipeDao.update(toDbRecipe(recipe));

        /* update category relations (delete and add newly) */
        // delete relations
        deleteCategoriesForRecipe(recipe);
        // add newly
        insertCategoriesForRecipe(recipe);

        /* update description images (delete and add newly) */
        // delete images
        deleteDescriptionImagesForRecipe(recipe);
        // add newly
        insertDescriptionImagesForRecipe(recipe);
    }

    @Override
    public void deleteRecipe(int recipeId) {
        Recipe recipe = findRecipeById(recipeId);

        // delete description images
        deleteDescriptionImagesForRecipe(recipe);

        // delete categories relations
        deleteCategoriesForRecipe(recipe);

        // delete recipe
        recipeDao.delete(recipeId);
    }
}
