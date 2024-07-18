import {defineStore} from 'pinia';
import axios from 'axios';

// const API_URL = "http://localhost:8080/api/products";
const API_URL = 'https://dummyjson.com/products';

export const useProductStore = defineStore('products', {
  state: () => ({
    products: [],
    product: {}
  }),
  actions: {
    async getProducts() {
      if (this.products.length > 0) return
      try {
        const res = await axios.get(API_URL).then((response) => response.data);
        this.products = res;
      } catch (error) {
        console.log("error in fetching products");
      }
    },
    async getProduct(id) {
      try {
        const res = await axios.get(`${API_URL}/${id}`).then((response) => response.data);
        this.product = res
        return res;
      } catch (error) {
        console.log("error in fetching product");
      }
    }
  }
})