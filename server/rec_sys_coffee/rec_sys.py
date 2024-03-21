import pandas as pd

#recommenders 
import random_forest
import content_based

# Function to read the CSV file and build the dictionary of users
def read_users_data(csv_file):
    users_data = {}
    with open(csv_file, 'r') as file:
        next(file)  # Skip the header
        for line in file:
            fields = line.strip().split(',')
            email = fields[0]
            immutables = tuple(fields[1].split(';'))
            purchase = tuple(fields[2:6])  # Include 'zucchero', 'aromi', 'momento_giornata', and 'stato_animo'
            label = fields[6]
            if email not in users_data:
                users_data[email] = {'immutables': immutables, 'previous_purchases': [purchase], 'label': [label]}
            else:
                users_data[email]['previous_purchases'].append(purchase)
                users_data[email]['label'].append(label)
    return users_data

# Example of usage
users_data = read_users_data('users_data.csv')

for email, data in users_data.items():
    print("Email:", email)
    print("Immutables:", data['immutables'])
    print("Previous purchases:", data['previous_purchases'])
    print("Label:", data['label'])
    print()

user_email = "user3@email.com"

# Train the model
trained_model, encoder = random_forest.train_and_recommend(users_data)

# New purchase features
new_purchase_features = [users_data[user_email]['immutables'],0, 1, 'evening', 'happy']

# Make prediction
predicted_label = random_forest.make_prediction(trained_model, encoder, new_purchase_features)
print("Predicted label for the new purchase:", predicted_label)

# Content-based recommendation
predicted_label_rec = content_based.content_based_recommendation(users_data, user_email, new_purchase_features)
print("Predicted label for the new purchase using content-based recommendation:", predicted_label_rec)