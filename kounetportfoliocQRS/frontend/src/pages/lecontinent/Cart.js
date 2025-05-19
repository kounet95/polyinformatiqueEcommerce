import React from 'react'
import NavBarComponent from '../component/NavBarComponent'
import LocatorComponent from '../component/LocatorComponent'
import CartDetailComponent from '../component/CartDetailComponent'

const Cart = () => {
  return (
    <>
         <NavBarComponent/>
         <LocatorComponent mainTitle="Cart" />
         <CartDetailComponent/>
    </>
  )
}

export default Cart
