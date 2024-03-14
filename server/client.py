import requests


# Constants
BASE_URL = "http://127.0.0.1:8000"  # Update this if your API is hosted elsewhere
TOKEN_URL = f"{BASE_URL}/token"
USERS_ME_URL = f"{BASE_URL}/users/me/"
USERS_ME_ITEMS_URL = f"{BASE_URL}/users/me/items/"


# Custom authentication class
class TokenAuth(requests.auth.AuthBase):
    """Implements a token authentication scheme."""

    def __init__(self, token):
        self.token = token

    def __call__(self, request):
        """Attach an API token to the Authorization header."""
        request.headers["Authorization"] = f"Bearer {self.token}"
        return request


def get_access_token(username: str, password: str) -> str:
    data = {
        "username": username,
        "password": password,
    }
    response = requests.post(TOKEN_URL, data=data)
    response.raise_for_status()  # This will raise an exception for HTTP error responses
    return response.json()["access_token"]


def get_user_details(access_token: str):
    response = requests.get(USERS_ME_URL, auth=TokenAuth(access_token))
    response.raise_for_status()  # Raises stored HTTPError, if one occurred
    return response.json()


def get_user_items(access_token: str):
    response = requests.get(USERS_ME_ITEMS_URL, auth=TokenAuth(access_token))
    response.raise_for_status()
    return response.json()


# Example usage
if __name__ == "__main__":
    username = "johndoe"  # Example user
    password = "secret"  # You'll need to replace this with the correct password

    # Authenticate and obtain token
    token = get_access_token(username, password)
    print("Access Token:", token)

    # Fetch user details
    user_details = get_user_details(token)
    print("User Details:", user_details)

    # Fetch user items
    user_items = get_user_items(token)
    print("User Items:", user_items)
