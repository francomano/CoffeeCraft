#!/usr/bin/env python
# coding: utf-8

# In[7]:


import xgboost as xgb
import numpy as np
import pandas as pd
import optuna
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder, OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline


# In[8]:


# load db

df = pd.read_csv("../../CoffeeCraft-server/backend/app/ml/data/model_data.csv")

X = df.drop('coffee_type', axis=1)
y = df['coffee_type']


# In[9]:


categorical_features = ['country', 'date', 'mood', 'sugar_level']
X = pd.get_dummies(X, columns=categorical_features)
X.head()


# In[10]:


label_encoder = LabelEncoder()
y_encoded = label_encoder.fit_transform(y)


# In[11]:


X_train, X_test, y_train, y_test = train_test_split(X, y_encoded, test_size=0.2, random_state=42)


# In[12]:


def objective(trial):
    params = {
        'verbosity': 0,
        'objective': 'multi:softmax',
        'num_class': len(y.unique()),
        'booster': trial.suggest_categorical('booster', ['gbtree', 'dart']),
        'lambda': trial.suggest_float('lambda', 1e-8, 1.0, log=True),
        'alpha': trial.suggest_float('alpha', 1e-8, 1.0, log=True),
        'max_depth': trial.suggest_int('max_depth', 3, 9),
        'eta': trial.suggest_float('eta', 0.01, 1.0, log=True),
        'gamma': trial.suggest_float('gamma', 1e-8, 1.0, log=True),
        'grow_policy': trial.suggest_categorical('grow_policy', ['depthwise', 'lossguide'])
    }
    model = xgb.XGBClassifier(**params)
    model.fit(X_train, y_train)
    preds = model.predict(X_test)
    accuracy = accuracy_score(y_test, preds)
    return accuracy


# In[ ]:


study = optuna.create_study(direction='maximize')
study.optimize(objective, n_trials=50)

# Affichage des meilleurs hyperparamètres
print("Meilleurs hyperparamètres:", study.best_trial.params)


# In[1]:


best_params = study.best_trial.params
model = xgb.XGBClassifier(**best_params, num_class=len(y.unique()))
model.fit(X_train, y_train)


# In[104]:


predictions = model.predict(X_test)
predictions_labels = label_encoder.inverse_transform(predictions)

print("Prédictions sur l'ensemble de test:",label_encoder.inverse_transform(predictions_labels.astype(int)))
predicted_labels = label_encoder.inverse_transform(predictions.astype(int))


# In[ ]:




