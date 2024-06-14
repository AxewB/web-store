<template>
  <v-sheet 
    width="100%" 
    height="100%"
  >
    <PageHeader/>
    <v-sheet 
      class="d-flex 
            flex-row 
            pa-2" 
      height="100%"
    >
      <v-sheet class="d-flex flex-column">
        <v-avatar size="150px" :image="userInfo.avatar"/>
        <v-list nav density="compact">
          <v-list-item 
            @click="window = 0" 
            :active="window === 0">
            Settings
          </v-list-item>
          <v-list-item 
            @click="window = 1"
            :active="window === 1">
            Payment info
          </v-list-item>
          <v-list-item 
            @click="window = 2"
            :active="window === 2">
            Order history
          </v-list-item>
        </v-list>
      </v-sheet>

      <v-divider vertical class="mx-2"/>

      <v-sheet class="flex-grow-1">
        <span class="text-h3">
          Profile
        </span>
        <v-divider/>
        <v-sheet class="d-flex flex-column">
          <v-window v-model="window">
            <v-window-item>
              <UserPageSettings/>
            </v-window-item>
            <v-window-item>
              <UserPagePayment/>
            </v-window-item>
            <v-window-item>
              <UserPageOrderHistory/>
            </v-window-item>
          </v-window>
        </v-sheet>
      </v-sheet>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import { ref } from 'vue'
import PageHeader from '@/components/PageHeader.vue'

import UserPageOrderHistory from '@/components/UserPageOrderHistory.vue'
import UserPagePayment from '@/components/UserPagePayment.vue'
import UserPageSettings from '@/components/UserPageSettings.vue'


const userInfo = ref({
  name: '',
  email: '',
  password: '',
  avatar: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmCy16nhIbV3pI1qLYHMJKwbH2458oiC9EmA&s'
})
const window = ref(0)


</script>
