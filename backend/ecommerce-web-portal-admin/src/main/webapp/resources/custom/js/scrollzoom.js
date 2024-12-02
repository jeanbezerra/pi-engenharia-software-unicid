document.addEventListener('DOMContentLoaded', function() {
	var isMouseDown = false;
	var startX;
	var startY;
	var treePositionX;
	var treePositionY;
	var treeContainer = document.getElementById('onmousewheel-id');
	var treeWidget;

	// Função para salvar o estado da árvore no Local Storage
	function saveTreeState() {
		localStorage.setItem('treePositionX', treeContainer.style.left);
		localStorage.setItem('treePositionY', treeContainer.style.top);
		localStorage.setItem('treeZoom', treeWidget.jq.css('transform'));
	}

	// Função para carregar o estado da árvore do Local Storage
	function loadTreeState() {
		var savedPositionX = localStorage.getItem('treePositionX');
		var savedPositionY = localStorage.getItem('treePositionY');
		var savedZoom = localStorage.getItem('treeZoom');

		if (savedPositionX && savedPositionY) {
			treeContainer.style.transition = 'left 0.5s, top 0.5s'; // Adiciona transição suave
			treeContainer.style.left = savedPositionX;
			treeContainer.style.top = savedPositionY;
		}

		if (savedZoom) {
			treeWidget.jq.css('transition', 'transform 0.5s'); // Adiciona transição suave
			treeWidget.jq.css('transform', savedZoom);
		}
	}

	function handleMouseWheel(event) {
		event.preventDefault();

		var currentZoom = parseFloat(treeWidget.jq.css('transform').replace('matrix(', '').split(',')[0]);

		if (isNaN(currentZoom)) {
			currentZoom = 1.0;
		}

		var delta = event.deltaY || event.detail || event.wheelDelta;
		var newZoom = currentZoom + (delta > 0 ? -0.1 : 0.1);
		var maxZoom = 1;
		var minZoom = 0.1;
		newZoom = Math.max(minZoom, Math.min(maxZoom, newZoom));

		// Get mouse position relative to container
		var rect = treeContainer.getBoundingClientRect();
		var mouseX = event.clientX - rect.left;
		var mouseY = event.clientY - rect.top;

		// **Re-centering logic:**
		// Verifique se os valores de rect.width e rect.height estão corretos
		console.log('Width:', rect.width);
		console.log('Height:', rect.height);

		// Certifique-se de que mouseX e mouseY estão corretos
		console.log('MouseX:', mouseX);
		console.log('MouseY:', mouseY);

		var offsetX = (mouseX - rect.width / 2) * (newZoom - currentZoom) / currentZoom;
		var offsetY = (mouseY - rect.height / 2) * (newZoom - currentZoom) / currentZoom;

		// Verifique se os valores de offsetX e offsetY estão corretos
		console.log('OffsetX:', offsetX);
		console.log('OffsetY:', offsetY);

		// Adjust container position for focus
		treeContainer.style.left = (treeContainer.offsetLeft - offsetX) + 'px';
		treeContainer.style.top = (treeContainer.offsetTop - offsetY) + 'px';


		// Apply zoom
		treeWidget.jq.css('transition', 'transform 0.5s'); // Adiciona transição suave
		treeWidget.jq.css('transform', 'scale(' + newZoom + ')');
		saveTreeState();
	}

	function handleMouseDown(e) {
		isMouseDown = true;
		startX = e.clientX;
		startY = e.clientY;
		treePositionX = parseInt(treeContainer.style.left);
		treePositionY = parseInt(treeContainer.style.top);
	}

	function handleMouseUp() {
		isMouseDown = false;
		document.removeEventListener('mousemove', handleMouseMove);
		saveTreeState(); 
	}

	function handleMouseMove(e) {
		if (!isMouseDown)
			return;

		var deltaX = e.clientX - startX;
		var deltaY = e.clientY - startY;

		treeContainer.style.left = (treePositionX + deltaX) + 'px';
		treeContainer.style.top = (treePositionY + deltaY) + 'px';

		// Update reference positions for the next mouse movement
		startX = e.clientX;
		startY = e.clientY;
		treePositionX = parseInt(treeContainer.style.left) || 0;
		treePositionY = parseInt(treeContainer.style.top) || 0;

		// Update content position on the page
		var content = document.getElementById('form:data-widget');
		content.style.left = treeContainer.style.left;
		content.style.top = treeContainer.style.top;
		saveTreeState();
	}

	// Agora vamos buscar o widget da árvore assim que o DOM estiver pronto
	treeWidget = PF('data-widget');

	// Carrega o estado da árvore ao carregar a página
	loadTreeState();

	// Adiciona os event listeners
	treeContainer.addEventListener('wheel', handleMouseWheel);
	treeContainer.addEventListener('mousedown', handleMouseDown);
	treeContainer.addEventListener('mouseup', handleMouseUp);
	treeContainer.addEventListener('mousemove', handleMouseMove);
});