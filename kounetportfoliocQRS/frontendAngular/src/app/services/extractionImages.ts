
const driveLinks = [
  "https://drive.google.com/file/d/1xcv6nZYobCt1hVgjWO8T4Y5ut-6Fv2ig/view?usp=drivesdk",
  "https://drive.google.com/file/d/12sj2z3kfwaEAn2bGgwwV06qzUUCC_JG8/view?usp=drivesdk",
  
];

// Fonction pour extraire l'ID d'un lien Drive
function extractDriveId(link) {
  // Si c'est déjà un ID
  if (/^[\w-]{10,}$/.test(link)) return link;
  // Sinon, on cherche l'ID dans le lien
  const match = link.match(/\/d\/([a-zA-Z0-9_-]+)/);
  return match ? match[1] : null;
}

driveLinks.forEach(link => {
  const id = extractDriveId(link);
  if (id) {
    const imageUrl = `https://drive.google.com/uc?export=view&id=${id}`;
    console.log(imageUrl);
  } else {
    console.log(`Lien invalide : ${link}`);
  }
});