import {defineStore} from 'pinia';
import axios from 'axios';

const API_URL = "localhost:8080/api/products";

export const useProductStore = defineStore('product', {
  state: () => ({
    products: []
  }),
  actions: {
    getProducts() {
      try {
        const res = axios.get(API_URL);
      } catch (error) {
        console.log("error in fetching products");
      }
    }
    // async setProducts() {
    //   try {
    //     const res = await fetch(API_URL, {
    //       method: "get",
    //       headers: {
    //         "Content-Type": "application/json"
    //       },
    //     })

    //     const data = await res.json();

    //     if (data.status === 200) {
    //       console.log("success in fetching products");
    //     }
    //     else {
    //       console.log("error in fetching products");
    //     }
    //   } catch (error) {
        
    //   }
    // }
  }
})