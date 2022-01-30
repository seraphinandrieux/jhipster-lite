import { shallowMount } from '@vue/test-utils';
import HomeVue from '@/common/primary/home/Home.vue';

describe('Home.vue', () => {
  it('renders props.msg when passed', () => {
    const wrapper = shallowMount(HomeVue, {});
    expect(wrapper.exists()).toBe(true);
  });
});
