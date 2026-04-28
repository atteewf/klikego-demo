<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription Course</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen">

    <div class="max-w-4xl mx-auto px-4 py-10">
        <h1 class="text-3xl font-bold text-center text-blue-700 mb-2">🏃 Courses disponibles</h1>
        <p class="text-center text-gray-500 mb-8">Inscrivez-vous à une course près de chez vous</p>

        <!-- Liste des courses -->
        <div class="grid gap-4 mb-10">
            <% 
                List<String[]> courses = (List<String[]>) request.getAttribute("courses");
                if (courses != null) {
                    for (String[] course : courses) {
            %>
            <div class="bg-white rounded-xl shadow p-5 flex justify-between items-center">
                <div>
                    <h2 class="text-lg font-semibold text-gray-800"><%= course[1] %></h2>
                    <p class="text-sm text-gray-500"><%= course[4] %> · <%= course[2] %> · <%= course[3] %></p>
                </div>
                <button 
                    onclick="selectCourse('<%= course[0] %>', '<%= course[1] %>')"
                    class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-700 transition">
                    S'inscrire
                </button>
            </div>
            <% } } %>
        </div>

        <!-- Formulaire d'inscription -->
        <div class="bg-white rounded-xl shadow p-6">
            <h2 class="text-xl font-bold text-gray-800 mb-4">Formulaire d'inscription</h2>
            <form action="/inscription-course/inscription" method="post" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Course sélectionnée</label>
                    <input type="text" id="course-nom" name="courseNom" readonly
                        class="w-full border border-gray-300 rounded-lg px-3 py-2 bg-gray-50 text-gray-500"
                        placeholder="Cliquez sur S'inscrire pour sélectionner une course"/>
                    <input type="hidden" id="course-id" name="courseId"/>
                </div>
                <div class="grid grid-cols-2 gap-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Nom</label>
                        <input type="text" name="nom" required
                            class="w-full border border-gray-300 rounded-lg px-3 py-2"/>
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1">Prénom</label>
                        <input type="text" name="prenom" required
                            class="w-full border border-gray-300 rounded-lg px-3 py-2"/>
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                    <input type="email" name="email" required
                        class="w-full border border-gray-300 rounded-lg px-3 py-2"/>
                </div>
                <button type="submit"
                    class="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition">
                    Confirmer l'inscription
                </button>
            </form>
        </div>
        <!-- Visualisation BDD -->
<div class="mt-10">
    <h2 class="text-2xl font-bold text-gray-800 mb-6 flex items-center gap-2">
        🗄️ Visualisation en direct de la base de données
    </h2>

    <!-- Table courses -->
    <div class="bg-white rounded-xl shadow p-6 mb-6">
        <div class="flex items-center gap-2 mb-4">
            <span class="bg-blue-100 text-blue-700 text-xs font-bold px-3 py-1 rounded-full">TABLE</span>
            <h3 class="text-lg font-bold text-gray-700">courses</h3>
        </div>
        <div class="overflow-x-auto">
            <table class="w-full text-sm text-left">
                <thead class="bg-gray-50 text-gray-500 uppercase text-xs">
                    <tr>
                        <th class="px-4 py-3">id</th>
                        <th class="px-4 py-3">nom</th>
                        <th class="px-4 py-3">date_course</th>
                        <th class="px-4 py-3">distance</th>
                        <th class="px-4 py-3">ville</th>
                    </tr>
                </thead>
                <tbody class="divide-y divide-gray-100">
                    <%
                        List<String[]> coursesDb = (List<String[]>) request.getAttribute("courses");
                        if (coursesDb != null) {
                            for (String[] c : coursesDb) {
                    %>
                    <tr class="hover:bg-gray-50">
                        <td class="px-4 py-3 text-gray-400"><%= c[0] %></td>
                        <td class="px-4 py-3 font-medium text-gray-800"><%= c[1] %></td>
                        <td class="px-4 py-3 text-gray-600"><%= c[2] %></td>
                        <td class="px-4 py-3 text-gray-600"><%= c[3] %></td>
                        <td class="px-4 py-3 text-gray-600"><%= c[4] %></td>
                    </tr>
                    <% } } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Table inscriptions -->
    <div class="bg-white rounded-xl shadow p-6">
        <div class="flex items-center gap-2 mb-4">
            <span class="bg-green-100 text-green-700 text-xs font-bold px-3 py-1 rounded-full">TABLE</span>
            <h3 class="text-lg font-bold text-gray-700">inscriptions</h3>
        </div>
        <div class="overflow-x-auto">
            <table class="w-full text-sm text-left">
                <thead class="bg-gray-50 text-gray-500 uppercase text-xs">
                    <tr>
                        <th class="px-4 py-3">id</th>
                        <th class="px-4 py-3">nom</th>
                        <th class="px-4 py-3">prenom</th>
                        <th class="px-4 py-3">email</th>
                        <th class="px-4 py-3">course</th>
                        <th class="px-4 py-3">date_inscription</th>
                    </tr>
                </thead>
                <tbody class="divide-y divide-gray-100">
                    <%
                        List<String[]> inscriptionsDb = (List<String[]>) request.getAttribute("inscriptions");
                        if (inscriptionsDb != null && !inscriptionsDb.isEmpty()) {
                            for (String[] i : inscriptionsDb) {
                    %>
                    <tr class="hover:bg-gray-50">
                        <td class="px-4 py-3 text-gray-400"><%= i[0] %></td>
                        <td class="px-4 py-3 font-medium text-gray-800"><%= i[1] %></td>
                        <td class="px-4 py-3 text-gray-600"><%= i[2] %></td>
                        <td class="px-4 py-3 text-gray-600"><%= i[3] %></td>
                        <td class="px-4 py-3">
                            <span class="bg-blue-50 text-blue-600 px-2 py-1 rounded text-xs"><%= i[4] %></span>
                        </td>
                        <td class="px-4 py-3 text-gray-400 text-xs"><%= i[5] %></td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="6" class="px-4 py-6 text-center text-gray-400">
                            Aucune inscription pour le moment
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
    </div>

    <script>
        function selectCourse(id, nom) {
            document.getElementById('course-id').value = id;
            document.getElementById('course-nom').value = nom;
        }
    </script>

</body>
</html>