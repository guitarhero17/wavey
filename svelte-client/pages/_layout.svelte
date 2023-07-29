<script>
  import Header from '../components/Header.svelte'
  import Footer from '../components/Footer.svelte'
  import '../global.css'
  import { fade } from 'svelte/transition'
</script>

<div class="font-glacial bg-black text-white min-h-screen">
  <Header/>
  <main transition:fade class="min-h-[1000px]">
    <slot/>
  </main>
  <Footer/>
</div>
