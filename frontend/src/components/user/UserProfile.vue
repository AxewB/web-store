<template>
  <v-sheet v-if="currentUser" class="container">
    asdfds
    {{ loggedIn }}
    <header class="jumbotron">
      <h3>
        <strong>{{currentUser.username}}</strong> Profile
      </h3>
    </header>
    <p>
      <strong>Token:</strong>
      {{currentUser.accessToken.substring(0, 20)}} ... {{currentUser.accessToken.substr(currentUser.accessToken.length - 20)}}
    </p>
    <p>
      <strong>Id:</strong>
      {{currentUser.id}}
    </p>
    <p>
      <strong>Email:</strong>
      {{currentUser.email}}
    </p>
    <strong>Authorities:</strong>
    <ul>
      <li v-for="role in currentUser.roles" :key="role">{{role}}</li>
    </ul>
    <v-btn @click="logout">LOGOUT</v-btn>
  </v-sheet>
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
const router = useRouter();
const userStore = useUserStore();

const currentUser = computed(() => {return userStore.user.user});

const logout = () => {
  userStore.logout();
}

const loggedIn = computed(() => {
    return userStore.user.status.loggedIn;
  },
)
onMounted(() => {
  if (!loggedIn.value) {
    router.push('/user/login');
  }
})


</script>