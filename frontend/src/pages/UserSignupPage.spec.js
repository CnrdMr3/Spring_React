/* eslint-disable testing-library/no-container */
import React from "react";
import { render } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import { UserSignupPage } from "./UserSignupPage";

describe("UserSignupPage", () => {
  describe("Layout", () => {
    it("has header if Sign Up", () => {
      const { container } = render(<UserSignupPage />);
      // eslint-disable-next-line testing-library/no-node-access
      const header = container.querySelector("h1");
      expect(header).toHaveTextContent("Sign Up");
    });
  });
});
