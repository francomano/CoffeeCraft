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
        immutables = user_data['immutables']
        for purchase, label in zip(user_data['previous_purchases'], user_data['label']):
            # Include immutable features along with purchase features
            X_entry = list(immutables) + list(purchase)
            X.append(X_entry)
            y.append(label)

    # One-hot encode categorical variables
    encoder = OneHotEncoder(sparse_output=False, handle_unknown='ignore')  # Set handle_unknown to 'ignore'
    categorical_cols = [0, 1, 2, 5, 6]  # Indices of categorical columns
    X_encoded = encoder.fit_transform(np.array(X)[:, categorical_cols])

    # Combine one-hot encoded features with numerical features
    numerical_cols = [3, 4]  # Indices of numerical columns
    X_final = np.concatenate([X_encoded, np.array(X)[:, numerical_cols]], axis=1)

    # Train the Random Forest Classifier model
    model = RandomForestClassifier(n_estimators=100, random_state=42)
    model.fit(X_final, y)

    return model, encoder

def make_prediction(model, encoder, new_purchase_features):
    # Extract immutable features for encoding
    immutable_features = [new_purchase_features[0][0],new_purchase_features[0][1],new_purchase_features[0][2]]

    # Extract categorical features for encoding
    categorical_features = immutable_features + new_purchase_features[3:5]
    numerical_features = new_purchase_features[1:3]  

    # Encode all categorical features together
    #print(categorical_features)
    encoded_categorical = encoder.transform([categorical_features])

    # Combine encoded categorical features with numerical features
    new_purchase_final = np.concatenate([encoded_categorical, [numerical_features]], axis=1)

    # Make prediction
    prediction = model.predict(new_purchase_final)

    save_decision_tree_image(model.estimators_[0], encoder, filename='decision_tree.png')

    return prediction[0]



def save_decision_tree_image(estimator, encoder, filename):
    # Get all feature names used during model training
    categorical_feature_names = encoder.get_feature_names_out()
    numerical_feature_names = ['zucchero', 'aromi']
    feature_names = np.concatenate((categorical_feature_names, np.array(numerical_feature_names)))

    dot_data = export_graphviz(estimator, out_file=None, 
                               feature_names=feature_names,
                               class_names=encoder.categories_[-1].tolist(),
                               filled=True, rounded=True, special_characters=True)
    graph = pydotplus.graph_from_dot_data(dot_data)
    graph.write_png(filename)
