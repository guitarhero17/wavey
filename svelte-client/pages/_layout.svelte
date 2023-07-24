<script>
  import Modal from 'svelte-simple-modal'
  import Header from '../components/Header.svelte'
  import Footer from '../components/Footer.svelte'
  import '../global.css'
  import { fade } from 'svelte/transition'
</script>

<div class="font-glacial bg-black text-white">
  <Modal
    classWindow="bg-gray-800 text-white rounded-3xl"
    classContent="px-16 h-[30rem] grid place-items-center s:pt-10 s:px-10 overflow-hidden"
    classCloseButton="custom-shadow-waveyYellow bg-transparent before:bg-waveyYellow after:bg-waveyYellow top-5 right-5 hover:scale-125"
  >
    <Header/>
    <main transition:fade>
      <slot/>
    </main>
    <Footer/>
  </Modal>
</div>
