import { defineStore } from 'pinia'
import axios from 'axios'
import contentHeader from '@/services/content-header';
import authHeader from '@/services/auth-header';
const API_URL = "http://localhost:8080/api/category";


export const useCategoryStore = defineStore('category', {
  state: () => ({
    categories: [],
    category: {},
    categoryPath: []
  }),
  getters: {
    rootCategories(state) {
      return state.categories.filter((cat) => cat.parent === null)
    },
    getCategoriesNames(state) {
      return state.categories.map((cat) => cat.name)
    },
    getCategoriesNamesAndIds(state) {
      return state.categories.map((cat) => ({ name: cat.name, id: cat.id }))
    }
  },
  actions: {
    async getCategories() {
      const res = await axios.get(API_URL).then((response) => response.data);
      this.categories = res;
    },
    async getCategoriesWithChildren() {
      const res = await axios.get(API_URL + "/all-with-children").then((response) => response.data);
      this.categories = res;
    },
    async getCategory(id) {
      try {
        const res = await axios.get(API_URL + `/${id}`).then((response) => response.data)
        return res;
      } catch (error) {
        console.log("error fetching categories");
      }
    },
    async getCategoryPath(id) {
      const res = await axios.get(API_URL + `/${id}/path`).then((response) => response.data)
      this.categoryPath = res;
    },

    async getChildren(parentId) {
      const res = await axios.get(API_URL + `/${parentId}/children`).then((response) => response.data)
      return res
    },

    async addCategory(category) {
      await this.getCategories();
      const parent = category.parent ? this.categories.filter((cat) => cat.id === category.parent) : null
      const data = {
        "name": category.name,
        "parent": parent
      }
      axios.post(API_URL, data, { headers: contentHeader() })
      .then(response => console.log(response))
      .catch(error => console.log(error));
    },

    async updateCategory(id, category) {
      await this.getCategories();
      const parent = category.parent ? this.categories.filter((cat) => cat.id === category.parent) : null
      const data = {
        name: category.name,
        parent: parent
      }

      axios.put(API_URL + `/${id}`, data, { headers: contentHeader() })
      .then(response => console.log('response', response))
      .catch(error => console.log('error', error));
    },

    async deleteCategory(id) {
      console.log('deleting')

      axios.delete(API_URL + `/${id}`, { headers: authHeader() })
      .then(response => console.log(response))
      .catch(error => console.log(error));
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