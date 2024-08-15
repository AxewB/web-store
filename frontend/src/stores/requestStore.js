import { defineStore } from 'pinia'

export const useRequestStore = defineStore('request', {
  state: () => ({
    snackbar: {
      show: false,
      text: '',
      color: ""
    },
    loading: {
      value: false,
      show() {
        this.value = true
      },
      hide() {
        this.value = false
      }
    }
  }),
  actions: {
    showSnackbar(text, color = 'success') {
      this.hideSnackbar()
      this.snackbar.text = text
      this.snackbar.color = color
      this.snackbar.show = true
    },
    hideSnackbar() {
      this.snackbar.show = false
      this.snackbar.text = ''
    },

    showLoading() {
      this.loading.show()
    },

    hideLoading(responseHandler, showSnackbar = true) {
      const {res, err} = responseHandler;
      // Показывает результат запроса, если он был передан
      if (showSnackbar) {
        if (res) {
          this.showSnackbar(res.message, 'success')
        }
        else if (err) {
          this.showSnackbar(err.message, 'error')
        }
      }
      this.loading.hide()
    }
  },
})