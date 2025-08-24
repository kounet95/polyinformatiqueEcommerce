#!/usr/bin/env python3
"""
Test script to verify stock validation functionality
"""
import requests
import json

# Configuration
QUERY_SERVICE_URL = "http://localhost:8082"
COMMAND_SERVICE_URL = "http://localhost:8081"

def test_stock_validation():
    """Test the stock validation endpoint"""
    print("=== Testing Stock Validation ===")

    # Test data - assuming we have some stock IDs
    validation_requests = [
        {
            "stockId": "test-stock-1",
            "requestedQuantity": 2
        },
        {
            "stockId": "test-stock-2",
            "requestedQuantity": 1
        }
    ]

    try:
        url = f"{QUERY_SERVICE_URL}/api/stocks/validate-availability"
        response = requests.post(url, json=validation_requests)

        print(f"Status Code: {response.status_code}")
        print(f"Response: {response.text}")

        if response.status_code == 200:
            result = response.json()
            print(f"Stock validation result: {result}")
            return result
        else:
            print(f"Error: {response.status_code} - {response.text}")
            return False

    except Exception as e:
        print(f"Exception during stock validation test: {e}")
        return False

def test_order_creation_with_validation():
    """Test order creation with stock validation"""
    print("\n=== Testing Order Creation with Stock Validation ===")

    # Test order data
    order_request = {
        "orderDTO": {
            "customerEmail": "test@example.com",
            "currency": "EUR",
            "total": 100.0,
            "paymentMethod": "CARD",
            "orderLines": [
                {
                    "stockId": "test-stock-1",
                    "qty": 2
                }
            ]
        },
        "custom": False
    }

    try:
        url = f"{COMMAND_SERVICE_URL}/order/command/create"
        response = requests.post(url, json=order_request)

        print(f"Status Code: {response.status_code}")
        print(f"Response: {response.text}")

        if response.status_code == 200:
            print("Order creation successful!")
            return True
        else:
            print(f"Order creation failed: {response.status_code} - {response.text}")
            return False

    except Exception as e:
        print(f"Exception during order creation test: {e}")
        return False

def test_order_creation_insufficient_stock():
    """Test order creation with insufficient stock"""
    print("\n=== Testing Order Creation with Insufficient Stock ===")

    # Test order data with high quantity to trigger insufficient stock
    order_request = {
        "orderDTO": {
            "customerEmail": "test@example.com",
            "currency": "EUR",
            "total": 1000.0,
            "paymentMethod": "CARD",
            "orderLines": [
                {
                    "stockId": "test-stock-1",
                    "qty": 999999  # Very high quantity to trigger insufficient stock
                }
            ]
        },
        "custom": False
    }

    try:
        url = f"{COMMAND_SERVICE_URL}/order/command/create"
        response = requests.post(url, json=order_request)

        print(f"Status Code: {response.status_code}")
        print(f"Response: {response.text}")

        if response.status_code == 500 and "Stock insuffisant" in response.text:
            print("Stock validation working correctly - insufficient stock detected!")
            return True
        else:
            print(f"Unexpected response: {response.status_code} - {response.text}")
            return False

    except Exception as e:
        print(f"Exception during insufficient stock test: {e}")
        return False

if __name__ == "__main__":
    print("Starting Stock Validation Tests...")
    print("Note: Make sure both ecPolyQuery (port 8082) and ecPolyCommand (port 8081) services are running")

    # Run tests
    test_stock_validation()
    test_order_creation_with_validation()
    test_order_creation_insufficient_stock()

    print("\nTests completed!")
