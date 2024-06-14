<template>
  <v-sheet>
    <v-text-field
      label="Имя"
      v-model="userInfo.name"/>
    <v-text-field
      label="E-mail"
      v-model="userInfo.email"/>
    <v-text-field
      label="Ссылка на картинку аватара"
      v-model="userInfo.avatar"/>
    <v-sheet class="d-flex flex-row align-center" >
      <v-text-field label="Пароль" type="password" hide-details/>
      
      <template #append>
        asdf
        <v-btn>
      Сбросить пароль
      </v-btn>
      </template>
      
    </v-sheet>
      
      <v-sheet class="d-flex justify-end">
          <v-btn 
              variant="flat" 
              color="primary">
            Сохранить
          </v-btn>
      </v-sheet>
    <v-alert
      type="error" 
      width="100%"
      variant="tonal"
      class="mt-2"
    >
    <v-sheet class="d-flex flex-column">
      <span class="text-error">
        Эта облать для непоправимых измненеий в аккаунте!
      </span>
      <v-sheet class="mt-2">
        <v-btn color="error" >Удалить аккаунт</v-btn>
      </v-sheet>
      
    </v-sheet>
    
    </v-alert>
      
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
