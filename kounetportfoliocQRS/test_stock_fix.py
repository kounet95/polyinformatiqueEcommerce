#!/usr/bin/env python3
"""
Test script to verify the stock API fix for order creation
"""

import requests
import json

def test_stock_api():
    """Test the stock API endpoint mentioned in the issue"""
    url = "http://localhost:8888/ecpolyquery/api/stocks/productsize/ecfcf80a-8427-4668-b991-d6efd88f90cc"

    try:
        print(f"Testing stock API: {url}")
        response = requests.get(url)

        if response.status_code == 200:
            stocks = response.json()
            print(f"✅ API returned {len(stocks)} stock(s)")

            for i, stock in enumerate(stocks):
                print(f"Stock {i+1}:")
                print(f"  - ID: {stock.get('id')}")
                print(f"  - Product Size ID: {stock.get('productSizeId')}")
                print(f"  - Supplier ID: {stock.get('supplierId')}")
                print(f"  - Quantity: {stock.get('quantity')}")
                print(f"  - Purchase Price: {stock.get('purchasePrice')}")
                print(f"  - Promo Price: {stock.get('promoPrice')}")
                print()

            # Verify the structure matches what the frontend expects
            if stocks and all(key in stocks[0] for key in ['id', 'productSizeId', 'quantity']):
                print("✅ Stock structure is correct for frontend consumption")
                return True
            else:
                print("❌ Stock structure is missing required fields")
                return False
        else:
            print(f"❌ API returned status code: {response.status_code}")
            print(f"Response: {response.text}")
            return False

    except requests.exceptions.ConnectionError:
        print("❌ Could not connect to the API. Make sure the backend is running on localhost:8888")
        return False
    except Exception as e:
        print(f"❌ Error testing API: {e}")
        return False

def main():
    print("=== Testing Stock API Fix ===")
    print()

    success = test_stock_api()

    print()
    print("=== Summary ===")
    if success:
        print("✅ Stock API is working correctly")
        print("✅ The fix should resolve the empty stockIds issue")
        print()
        print("Next steps:")
        print("1. Start the frontend application")
        print("2. Add a product to cart")
        print("3. Check that stockIds are populated in the cart")
        print("4. Create an order and verify stockIds are sent correctly")
    else:
        print("❌ Stock API test failed")
        print("Please ensure the backend is running and the API endpoint is accessible")

if __name__ == "__main__":
    main()
