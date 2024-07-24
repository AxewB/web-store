import {defineStore} from 'pinia';
import axios from 'axios';

// const API_URL = "http://localhost:8080/api/products";
const API_URL = 'https://dummyjson.com/products';

export const useProductStore = defineStore('products', {
  state: () => ({
    products: [],
    total: 0,
    skip: 0,
    limit: 10,
    page: 1,
  }),
  getters: {
    totalPages: (state) => Math.ceil(state.total / state.limit),
  },
  actions: {
    async getProducts() {
      try {
        const res = await axios.get(API_URL + `?skip=${this.skip}&limit=${this.limit}`).then((response) => response.data);
        this.products = res.products
        this.total = res.total
        this.skip = res.skip
        this.limit = res.limit
      } catch (error) {
        console.log("error in fetching products");
      }
    },
    async getProduct(id) {
      try {
        const res = await axios.get(`${API_URL}/${id}`).then((response) => response.data);
        return res
      } catch (error) {
        console.log("error in fetching product");
      }
    },
    async turnPage(page) {
      this.page = page
      this.skip = (this.page - 1) * this.limit
      console.log(this.skip)
      await this.getProducts()
    },
    resetStore() { 
      this.products = []
      this.total = 0
      this.skip = 0
      this.limit = 10
      this.page = 1
    },
    editProduct(oldProduct, newProduct) {
      alert("Not implemented yet!")
    }
  }
})