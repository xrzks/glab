import { createContext, type ParentComponent, useContext } from "solid-js";
import { createStore } from "solid-js/store";

interface AuthContextType {
	email: string | null;
	isAuthenticated: () => boolean;
}

const AuthContext = createContext<AuthContextType>();

export const AuthProvider: ParentComponent = (props) => {
	const [state] = createStore({
		get email() {
			return typeof window !== "undefined"
				? sessionStorage.getItem("email")
				: null;
		},
	});

	const isAuthenticated = () => state.email !== null;

	const value: AuthContextType = {
		get email() {
			return state.email;
		},
		isAuthenticated,
	};

	return (
		<AuthContext.Provider value={value}>{props.children}</AuthContext.Provider>
	);
};

export const useAuth = () => {
	const context = useContext(AuthContext);
	if (!context) throw new Error("useAuth must be used within an AuthProvider");
	return context;
};
