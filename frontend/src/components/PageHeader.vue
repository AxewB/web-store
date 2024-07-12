<template>
  <v-toolbar
    height="60px"
    width="100%"
    class="pa-2 bg-transparent">
    <v-sheet
      width="100%"
      height="100%"
      class="d-flex 
            flex-row 
            align-center 
            rounded
            bg-transparent">
      <v-sheet class="bg-transparent">
        <v-btn :to="{ name: 'home' }">
          Catalogue
        </v-btn>
      </v-sheet>
      <v-sheet class="flex-grow-1 d-flex justify-end bg-transparent">
        <v-btn 
          icon
          class="mr-2"
          :to="{ name: 'cart' }"
          variant="text">
          <v-icon>
            mdi-cart
          </v-icon>
        </v-btn>
        <v-btn 
          icon
          variant="text"
          :to="{ name: 'profile' }">
          <v-icon>
            mdi-account
          </v-icon>
        </v-btn>
      </v-sheet>
  </v-sheet>
    
  </v-toolbar>
</template>

<script setup>

</script>
