<template>
  <v-sheet v-if="currentUser" height="100%" class="d-flex flex-column">
      <AppHeader/>

      <v-container fluid class="flex-grow-1" >
        <v-row  class="fill-height">
          <v-col class=" ma-1" cols="2">
            <v-row justify="center" class=" pa-2">
              <v-sheet class="d-flex flex-column align-center pa-2" elevation="10" width="100%" rounded>
              <v-avatar size="200px" rounded :image="currentUser.avatar ? currentUser.avatar : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmCy16nhIbV3pI1qLYHMJKwbH2458oiC9EmA&s'"/>
            </v-sheet>
            </v-row>
            <v-row justify="center" class="pa-2">
              <v-list nav rounded density='compact' width="100%" v-model="window" elevation="10">
                <v-list-item
                  link
                  @click="window = 0"
                  prepend-icon="mdi-account"
                  title="Account"/>
                  <v-list-item
                  link
                  @click="window = 1"
                  prepend-icon="mdi-package-variant"
                  title="Orders"/>
                  <v-list-item
                  link
                  @click="window = 2"
                  prepend-icon="mdi-credit-card"
                  title="Payment"/>
                  <v-list-item
                  link
                  @click="window = 3"
                  prepend-icon="mdi-cog"
                  title="Settings"/>
                <VDivider/>
                <v-list-item
                  @click="logout"
                  color="error"
                  width="100%"
                  title="Logout"
                  prepend-icon="mdi-logout"/>
              </v-list>
            </v-row>
          </v-col>
          <v-col class="  ma-1">
            <v-window v-mode="window">
              <v-window-item>
                <component :is="UserInfo"/>
              </v-window-item>
              <v-window-item>
                <component :is="UserOrders"/>
              </v-window-item>
              <v-window-item>
                <component :is="UserPayment"/>
              </v-window-item>
              <v-window-item>
                <component :is="UserSettings"/>
              </v-window-item>
            </v-window>
          </v-col>
        </v-row>
      </v-container>
  </v-sheet>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
import { computed, onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import UserInfo from "@/components/user/UserInfo.vue"
import UserPayment from "@/components/user/UserPayment.vue"
import UserOrders from './UserOrders.vue';
import UserSettings from "@/components/user/UserSettings.vue"

const router = useRouter();
const userStore = useUserStore();
const window = ref(0)


const currentUser = computed(() => {return userStore.user});

const logout = () => {
  userStore.logout();
}

const loggedIn = computed(() => {
    return userStore.status.loggedIn;
  },
)
onMounted(() => {
  if (!loggedIn.value) {
    router.push('/user/login');
  }
})


</script>