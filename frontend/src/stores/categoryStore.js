import { defineStore } from 'pinia'
import axios from 'axios'
import { useProductStore } from './productStore';
const API_URL = "http://localhost:8080/api/category";

export const useCategoryStore = defineStore('categories', {
  state: () => ({
    categories: [],
    category: {},
    categoryPath: []
  }),
  actions: {
    async getCategories() {
      const res = await axios.get(API_URL).then((response) => response.data);
      this.categories = res.data;
    },
    async getCategory(id) {
      try {
        const res = await axios.get(API_URL + `/${id}/`).then((response) => response.data)
        this.category = res;
      } catch (error) {
        console.log("error fetching category");
      }
    },
    async getCategoryPath(id) {
      console.log(id)
      const res = await axios.get(API_URL + `/${id}/path`).then((response) => response.data)
      console.log(res)
      this.categoryPath = res;
    },
    initCategories() {
      this.getCategories();
    },
    initCategoriesForProduct(id) {
      this.getCategory(id);
      this.getCategoryPath(id);
    }
  }
})