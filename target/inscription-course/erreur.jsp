<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="UTF-8" />
    <title>Erreur</title>
    <script src="https://cdn.tailwindcss.com"></script>
  </head>
  <body class="bg-gray-50 min-h-screen flex items-center justify-center">
    <div class="bg-white rounded-xl shadow p-10 text-center max-w-md">
      <div class="text-5xl mb-4">😬</div>
      <h1 class="text-2xl font-bold text-red-600 mb-2">
        Une erreur est survenue
      </h1>
      <p class="text-gray-500 mb-6">Veuillez réessayer.</p>
      <a
        href="/inscription-course/courses"
        class="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition"
      >
        Retour aux courses
      </a>
    </div>
  </body>
</html>
