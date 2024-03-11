import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

def content_based_recommendation(users_data, user_email, new_purchase_features):
    # Extract user's previous purchases
    previous_purchases = users_data[user_email]['previous_purchases']

    # Convert the list of previous purchases into a dataframe
    df = pd.DataFrame(previous_purchases, columns=['Zucchero', 'Aromi', 'Momento Giornata', 'Stato Animo'])

    # Combine all features into one string
    df['Features'] = df.apply(lambda row: ','.join(map(str, row)), axis=1)

    # Initialize a TF-IDF Vectorizer
    tfidf_vectorizer = TfidfVectorizer()

    # Fit and transform the features
    tfidf_features = tfidf_vectorizer.fit_transform(df['Features'])

    # Calculate similarity between previous purchases and the new purchase
    new_purchase_features_str = ','.join(map(str, new_purchase_features))  # Convert new purchase features to a string
    new_purchase_tfidf = tfidf_vectorizer.transform([new_purchase_features_str])

    # Calculate cosine similarity
    similarities = cosine_similarity(new_purchase_tfidf, tfidf_features)

    # Find the most similar previous purchase
    most_similar_index = similarities.argmax()

    # Get the corresponding label
    predicted_label = users_data[user_email]['label'][most_similar_index]

    return predicted_label
