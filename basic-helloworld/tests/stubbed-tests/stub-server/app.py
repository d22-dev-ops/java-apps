from flask import Flask, jsonify
import random

app = Flask(__name__)

response_sets = [
    {
        "status": "success",
        "data": {
            "make": "Tesla",
            "model": "Model 3",
            "year": 2021,
            "color": "Midnight Silver Metallic",
            "vin": "5YJ3E1EA8MF000000"
        },
        "message": "Car details retrieved successfully"
    },
    {
        "status": "success",
        "data": {
            "make": "Ford",
            "model": "Mustang Mach-E",
            "year": 2021,
            "color": "Rapid Red Metallic",
            "vin": "3FMTK1SS5MMA00000"
        },
        "message": "Car details retrieved successfully"
    },
    {
        "status": "success",
        "data": {
            "make": "Chevrolet",
            "model": "Bolt EV",
            "year": 2020,
            "color": "Slate Gray Metallic",
            "vin": "1G1FY6S00L4123456"
        },
        "message": "Car details retrieved successfully"
    },
    {
        "status": "success",
        "data": {
            "make": "Toyota",
            "model": "Prius",
            "year": 2019,
            "color": "Sea Glass Pearl",
            "vin": "JTDKARFU9K3070000"
        },
        "message": "Car details retrieved successfully"
    }
]

@app.route('/getcardetails')
def get_car_details():
    # Select a random response set
    response = random.choice(response_sets)
    return jsonify(response)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
