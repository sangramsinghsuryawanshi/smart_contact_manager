let currntTheme = getTheme();

// Apply the theme immediately when the page loads
document.querySelector("html").classList.add(currntTheme);
document.querySelector("#change-theme span").textContent =
  currntTheme === "light" ? "Dark" : "Light";

changeTheme();

function changeTheme() {
  const changeThemeButton = document.querySelector("#change-theme");
  changeThemeButton.addEventListener("click", (event) => {
    const oldTheme = currntTheme;
    if (currntTheme === "dark") {
      currntTheme = "light";
    } else {
      currntTheme = "dark";
    }
    setTheme(currntTheme);
    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(currntTheme);
    changeThemeButton.querySelector("span").textContent =
      currntTheme === "light" ? "Dark" : "Light";
  });
}

function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}
