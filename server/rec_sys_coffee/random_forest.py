from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import OneHotEncoder
import numpy as np
import graphviz
from sklearn.tree import export_graphviz
import pydotplus

def train_and_recommend(users_data):
    # Prepare the data
    X = []
    y = []
    for user_data in users_data.values():
        for purchase, label in zip(user_data['previous_purchases'], user_data['label']):
            X.append(purchase)
            y.append(label)

    # One-hot encode categorical variables
    encoder = OneHotEncoder(sparse_output=False, handle_unknown='ignore')  # Set handle_unknown to 'ignore'
    categorical_cols = [2, 3]  # Indices of categorical columns (momento_giornata, stato_animo)
    X_encoded = encoder.fit_transform(np.array(X)[:, categorical_cols])

    # Combine one-hot encoded features with numerical features
    numerical_cols = [0, 1]  # Indices of numerical columns (zucchero, aromi)
    X_final = np.concatenate([X_encoded, np.array(X)[:, numerical_cols]], axis=1)

    # Train the Random Forest Classifier model
    model = RandomForestClassifier(n_estimators=100, random_state=42)
    model.fit(X_final, y)

    return model, encoder

def make_prediction(model, encoder, new_purchase_features):
    # Extract immutable features for encoding
    immutable_features = new_purchase_features[1]

    # Extract categorical features for encoding
    categorical_cols = [4, 5]
    new_purchase_categorical = [new_purchase_features[i] for i in categorical_cols]

    # Prepare the new purchase features by encoding each categorical feature individually
    new_purchase_encoded = []

    encoded_feature = encoder.transform([new_purchase_categorical])
    new_purchase_encoded.append(encoded_feature)

    # Concatenate the encoded features
    new_purchase_encoded = np.concatenate(new_purchase_encoded, axis=1)

    # Extract numerical features
    numerical_features = new_purchase_features[2:4]

    # Combine encoded categorical features with numerical features
    #print(new_purchase_encoded, numerical_features)
    new_purchase_final = np.concatenate([new_purchase_encoded, [numerical_features]], axis=1)

    # Make prediction
    prediction = model.predict(new_purchase_final)

   # Get the index of the predicted class label
    label_index = np.where(model.classes_ == prediction)[0][0]

    # Save decision tree image of the estimator that made the prediction
    save_decision_tree_image(model.estimators_[label_index], encoder, filename='decision_tree.png')

    return prediction[0]

def save_decision_tree_image(estimator, encoder, filename):
    # Get all feature names used during model training
    feature_names = encoder.get_feature_names_out(input_features=['momento_giornata', 'stato_animo'])
    feature_names = np.concatenate((feature_names,np.array(['zucchero','aromi'])))

    dot_data = export_graphviz(estimator, out_file=None, 
                               feature_names=feature_names,
                               class_names=encoder.categories_[-1].tolist(),
                               filled=True, rounded=True, special_characters=True)
    graph = pydotplus.graph_from_dot_data(dot_data)
    graph.write_png(filename)

