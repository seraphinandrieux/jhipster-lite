import { createApp } from 'vue';
import AppVue from './common/primary/app/App.vue';
import router from './router';

createApp(AppVue).use(router).mount('#app');
