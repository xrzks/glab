export default function About() {
	return (
		<div class="min-h-screen bg-purple-50">
			{/* Main Content */}
			<main class="container mx-auto px-8 py-12">
				{/* Title section */}
				<div class="mb-12">
					<h2 class="text-5xl font-bold">About us</h2>
				</div>

				{/* First Content Row */}
				<div class="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-12">
					<div class="text-lg leading-relaxed">
						<p>
							Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
							nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam
							erat, sed diam voluptua. At vero eos et accusam et justo duo
							dolores et ea rebum. Stet clita kasd gubergren, no sea takimata
							sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit
							amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor
							invidunt ut labore et dolore magna aliquyam erat, sed diam
							voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
							Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum
							dolor sit amet.
						</p>
					</div>
					<div class="bg-gray-300 rounded-lg aspect-video overflow-hidden">
						<img
							src="https://placehold.co/800x450?text=About+Us"
							alt="About us"
							class="w-full h-full object-cover"
						/>
					</div>
				</div>

				{/* Second Content Row */}
				<div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
					<div class="bg-gray-300 rounded-lg aspect-video overflow-hidden">
						<img
							src="https://placehold.co/800x450?text=Our+Team"
							alt="Our team"
							class="w-full h-full object-cover"
						/>
					</div>
					<div class="text-lg leading-relaxed">
						<p>
							Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
							nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam
							erat, sed diam voluptua. At vero eos et accusam et justo duo
							dolores et ea rebum. Stet clita kasd gubergren, no sea takimata
							sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit
							amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor
							invidunt ut labore et dolore magna aliquyam erat, sed diam
							voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
							Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum
							dolor sit amet.
						</p>
					</div>
				</div>
			</main>
		</div>
	);
}
