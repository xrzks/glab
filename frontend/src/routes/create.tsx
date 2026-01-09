import { createSignal } from "solid-js";
import { useNavigate } from "@solidjs/router";
import { Button } from "~/components/ui/button";
import { createContainer as apiCreateContainer } from "~/lib/api";

export default function Create() {
	const navigate = useNavigate();
	const [name, setName] = createSignal("");
	const [description, setDescription] = createSignal("");
	const [image, setImage] = createSignal<File | null>(null);
	const [imagePreview, setImagePreview] = createSignal<string | null>(null);
	const [loading, setLoading] = createSignal(false);
	const [error, setError] = createSignal<string | null>(null);

	const handleImageUpload = (e: Event) => {
		const target = e.target as HTMLInputElement;
		const file = target.files?.[0];
		if (file) {
			setImage(file);
			const reader = new FileReader();
			reader.onloadend = () => {
				setImagePreview(reader.result as string);
			};
			reader.readAsDataURL(file);
		}
	};

	const handleCreate = async () => {
		setLoading(true);
		setError(null);

		try {
			const request = {
				name: name(),
				description: description(),
			};
			const response = await apiCreateContainer(request);
			console.log("Container created:", response);
			navigate("/");
		} catch (err) {
			setError(err instanceof Error ? err.message : "Failed to create container");
		} finally {
			setLoading(false);
		}
	};

	const handleCancel = () => {
		setName("");
		setDescription("");
		setImage(null);
		setImagePreview(null);
	};

	return (
		<div class="min-h-screen flex items-center justify-center pt-20 px-4">
			<div class="w-full max-w-md">
				<h1 class="text-4xl font-bold text-center mb-12">
					Insert new Container
				</h1>

				{error() && (
					<div class="mb-6 p-4 bg-red-100 border border-red-400 text-red-700 rounded-lg">
						{error()}
					</div>
				)}

				<div class="bg-purple-600 rounded-3xl p-8 shadow-lg">
					{/* Name Field */}
					<div class="mb-6">
						<label
							for="container-name"
							class="block text-purple-900 font-medium mb-2"
						>
							Name
						</label>
						<input
							id="container-name"
							type="text"
							placeholder="Value"
							value={name()}
							onInput={(e) => setName(e.currentTarget.value)}
							class="w-full px-4 py-2 rounded-lg bg-purple-50 border-none outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400"
						/>
					</div>

					{/* Description Field */}
					<div class="mb-6">
						<label
							for="container-description"
							class="block text-purple-900 font-medium mb-2"
						>
							Beschreibung
						</label>
						<textarea
							id="container-description"
							placeholder="Value"
							value={description()}
							onInput={(e) => setDescription(e.currentTarget.value)}
							rows="5"
							class="w-full px-4 py-2 rounded-lg bg-purple-50 border-none outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 resize-none"
						/>
					</div>

					{/* Image Upload Field */}
					<div class="mb-8">
						<label
							for="container-image"
							class="block text-purple-900 font-medium mb-2"
						>
							Lade Bilder hoch
						</label>
						<label
							for="container-image"
							class="block w-full h-48 bg-purple-50 rounded-lg cursor-pointer hover:bg-purple-100 transition-colors"
						>
							<input
								id="container-image"
								type="file"
								accept="image/*"
								onChange={handleImageUpload}
								class="hidden"
							/>
							<div class="w-full h-full flex items-center justify-center">
								{imagePreview() ? (
									<img
										src={imagePreview() || ""}
										alt="Container preview"
										class="max-w-full max-h-full object-contain rounded-lg"
									/>
								) : (
									<svg
										class="w-20 h-20 text-purple-900"
										fill="currentColor"
										viewBox="0 0 24 24"
										role="img"
										aria-label="Upload icon"
									>
										<title>Upload icon</title>
										<path d="M9 16h6v-6h4l-7-7-7 7h4zm-4 2h14v2H5z" />
									</svg>
								)}
							</div>
						</label>
					</div>

					{/* Action Buttons */}
					<div class="flex gap-4 justify-center">
						<Button
							onClick={handleCreate}
							disabled={loading()}
							class="bg-purple-900 hover:bg-purple-950 text-white px-8 py-2 rounded-lg font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
						>
							{loading() ? "Creating..." : "Create"}
						</Button>
						<Button
							onClick={handleCancel}
							disabled={loading()}
							variant="outline"
							class="bg-purple-50 hover:bg-purple-100 text-purple-900 px-8 py-2 rounded-lg font-medium border-none transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
						>
							Cancel
						</Button>
					</div>
				</div>
			</div>
		</div>
	);
}
