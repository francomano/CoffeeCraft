
# Start the FastAPI the server
uvicorn server:app --reload

# Run the client against the server (python requests)
python3 client.py