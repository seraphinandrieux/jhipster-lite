import { shallowMount } from '@vue/test-utils';
import AppVue from '@/common/primary/app/App.vue';

describe('App.vue', () => {
  it('renders props.msg when passed', () => {
    const wrapper = shallowMount(AppVue, {});
    expect(wrapper.exists()).toBe(true);
  });
});
