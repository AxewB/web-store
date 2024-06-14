<template>
  <v-sheet>
    <v-text-field
     label="Name"
     v-model="userInfo.name"/>
  </v-sheet>
</template>

<script setup>
import { ref } from 'vue'

const userInfo = ref({
  name: '',
  email: '',
  password: '',
  avatar: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmCy16nhIbV3pI1qLYHMJKwbH2458oiC9EmA&s'
})

</script>
