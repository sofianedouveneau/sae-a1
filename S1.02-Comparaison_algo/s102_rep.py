#                        Fichier réponse de la S102
# 1) --------------------------------------------------------------------------- #

fileOpen = open("questionnaire_premiere_annee_10q.txt", "r", encoding="utf-8")
fileQst = fileOpen.readlines()
fileOpen.close()

def create_answers_from_text_file(file):
    
    """
    Crée un dictionnaire de réponses à partir du contenu d'un fichier texte.

    Chaque ligne correspond à un élève et est de la forme :
    Nom Prénom:rep1/rep2/.../rep10
    """
    
    lignes = file
    dico = {}
    
    for ligne in lignes:
        
        parts = ligne.split(":")
        noms = parts[0].strip()

        
        valeurs = parts[1].strip()
        
        reponses = []
        for v in valeurs.split("/"):
          reponses.append(int(v))
            
        dico[noms] = reponses
    
    return dico

# 2) --------------------------------------------------------------------------- #
        
dico = create_answers_from_text_file(fileQst)

answerA = dico["Lisa Fischer"]
answerB = dico["Donna Weiss"]

import math

def Euclidean_distance(answerA, answerB):
    
    """
    Calcule la distance euclidienne entre deux réponses.
    """
    
    
    somme = 0
    for i in range(0, 10):
        
        somme += (int(answerA[i]) - int(answerB[i])) ** 2
    
    eucli = math.sqrt(somme)
    
    return eucli

# 3) --------------------------------------------------------------------------- #

import json

fileOpen_Ref = open("houses_ref.json", "r", encoding="utf-8")
HousesRef = json.load(fileOpen_Ref)
fileOpen_Ref.close()


def Euclidean_house(answer, ref):
    
    """
    Détermine la maison la plus proche d'une réponse donnée
    à partir d'un ensemble de références.
    """
    
    distance_min = Euclidean_distance(answer, ref[0]["answer"])
    maison = ref[0]["house"]
    
    for i in range(1, len(ref)):
        
        eucli = Euclidean_distance(answer, ref[i]["answer"])
        
        if eucli < distance_min: 
            
            distance_min = eucli
            maison = ref[i]["house"]
            
    return maison
        
# 4) --------------------------------------------------------------------------- #

def Euclidean_repartition(dico, ref):
    
    """
    Affecte chaque élève à une maison avec la méthode
    du plus proche voisin (distance euclidienne).
    """
    
    dicoRepartition = {}
    
    reponses = list(dico.values())
    noms = list(dico.keys())
    
    for i in range(len(reponses)) :
        
        answer = reponses[i]
        dicoRepartition[noms[i]] = Euclidean_house(answer, ref)
        
        if dicoRepartition[noms[i]] == None:
            
            dicoRepartition.pop(noms[i])
            
    return dicoRepartition

import s101

d1 = Euclidean_repartition(dico, HousesRef)

reponses = s101.lecture_reponses("questionnaire_premiere_annee_10q.txt")
d2 = s101.repartition(reponses)

nb_eleves = len(s101.eleves(reponses))
pourcentage = round((s101.nb_erreurs(d1, d2)/nb_eleves)*100, 2)
#print("Il y a", pourcentage,"% d'erreur entre les deux méthodes")
#La meilleure méthode est la méthode à distance Euclidienne. 

# 5) --------------------------------------------------------------------------- #

    
fileOpen_RefMultiple = open("houses_multiple_refs.json", "r", encoding="utf-8")
refMultiple = json.load(fileOpen_RefMultiple)
fileOpen_RefMultiple.close()

d1 = Euclidean_repartition(dico, refMultiple)
pourcentage = round((s101.nb_erreurs(d1, d2)/nb_eleves)*100, 2)
#print("Il y a", pourcentage,"% d'erreur entre les deux méthodes")
#Oui ça change la précision de la répartition. Avec une référence limitée à 4 réponses, la précision de la répartition augmente. C'est dû à la nature -
# - des réponses. En ayant des réponses dites "centrales", on élimine la possibilité à des réponses incohérentes. 

# 6) --------------------------------------------------------------------------- #

neighbors = HousesRef
ref = {"house": "Serdaigle","answer": [10, 10, 10, 10, 10, 10, 10, 10, 10, 10]}

def insertion_position_NN(answer, ref, neighbors):
    
    """
    Détermine l'indice où insérer une référence afin de conserver
    l'ordre des voisins du plus proche au moins proche.
    """
    
    distanceEucli = Euclidean_distance(answer, ref["answer"])
    
    for i in range(len(neighbors)):
        
        distanceNeighbor = Euclidean_distance(answer, neighbors[i]["answer"])
        
        if distanceEucli <= distanceNeighbor:
            return i
        
    return len(neighbors)

#print(insertion_position_NN([10, 10, 10, 10, 10, 10, 10, 10, 10, 10], ref, neighbors))

# 7) --------------------------------------------------------------------------- #

def insertion_NN(answer, ref, neighbors, k):

    """
    Insère une référence dans la liste des k plus proches voisins.

    La liste neighbors reste triée et sa taille ne dépasse jamais k.
    """

    position = insertion_position_NN(answer, ref, neighbors)

    if position < k:
        neighbors.insert(position, ref)

        if len(neighbors) > k:
            neighbors.pop()

    return neighbors

ref =   {"house": "Serdaigle", "answer": [1, 1, 1, 1, 1, 1, 1, 1, 1, 1] }
#print(insertion_NN([10, 10, 10, 10, 10, 10, 10, 10, 10, 10], ref, neighbors, 5))

# 8) --------------------------------------------------------------------------- #

def NN(answer, refs, k):

    """
    Calcule les k plus proches voisins d'une réponse donnée.
    """

    neighbors = []
    i = 0

    while i < len(refs):

        ref = refs[i]

        insertion_NN(answer, ref, neighbors, k)

        i += 1

    return neighbors

answer = [2, 1, 5, 6, 8, 2, 4, 3, 5, 9]
#print(NN(answer, HousesRef, 2))

# 9) --------------------------------------------------------------------------- #

def NN_house(neighbors):

    """
    Détermine la maison à partir des k plus proches voisins.

    La maison majoritaire est choisie.
    En cas d'égalité, on retourne la maison du voisin le plus proche.
    """

    apparition = {}
    i = 0

    while i < len(neighbors):
        maison = neighbors[i]["house"]

        if maison in apparition:
            apparition[maison] += 1 
        else:
            apparition[maison] = 1

        i += 1


    occurence = 0
    for maison in apparition:
        if apparition[maison] > occurence:
            occurence = apparition[maison]


    maisons_freq = []
    for maison in apparition:
        if apparition[maison] == occurence:
            maisons_freq.append(maison)


    if len(maisons_freq) == 1:
        return maisons_freq[0]


    return neighbors[0]["house"]

neighborsA = [{"house": "Serpentard","answer": [4, 6, 5, 9, 1, 7, 3, 10, 9, 8]},{"house": "Gryffondor", "answer": [3, 4, 9, 3, 6, 5, 10, 1, 9, 9]}, {"house": "Serdaigle","answer": [2, 10, 4, 5, 2, 10, 4, 3, 7, 3]}, {"house": "Gryffondor", "answer": [9, 3, 6, 2, 10, 2, 5, 1, 8, 2]}]
neighborsB = [{"house": "Serpentard", "answer": [4, 6, 5, 9, 1, 7, 3, 10, 9, 8]},{"house": "Gryffondor", "answer": [3, 4, 9, 3, 6, 5, 10, 1, 9, 9]},{"house": "Serdaigle", "answer": [2, 10, 4, 5, 2, 10, 4, 3, 7, 3]},{"house": "Gryffondor","answer": [9, 3, 6, 2, 10, 2, 5, 1, 8, 2]},{"house": "Serpentard", "answer": [8, 6, 6, 10, 8, 5, 5, 6, 7, 8]}]

#print(NN_house(neighborsA))

# 10) -------------------------------------------------------------------------- #

def NN_repartition(dico_reponses, refs, k):

    """
    Affecte chaque élève à une maison avec la méthode
    des k plus proches voisins.
    """

    dico_affectation = {}

    noms = list(dico_reponses.keys())
    reponses = list(dico_reponses.values())

    i = 0
    while i < len(noms):
        answer = reponses[i]
        neighbors = NN(answer, refs, k) 
        maison = NN_house(neighbors)
        dico_affectation[noms[i]] = maison

        i += 1
        
    return dico_affectation

reponses = s101.lecture_reponses("questionnaire_premiere_annee_10q.txt")
repartition_choixpeau = s101.repartition(reponses)

nb_eleves = len(s101.eleves(reponses))

for k in range(1, 6):
    repartition_knn = NN_repartition(dico, HousesRef, k)    
    erreurs = s101.nb_erreurs(repartition_knn, repartition_choixpeau)
    pourcentage = round((erreurs / nb_eleves) * 100, 2)
    #print("k = ",k, "→", pourcentage, "%", "d'erreur")
    
    

# ------------------------------ Tests Unitaires ------------------------------- #

def run_tests():
    
    """Exécute une série de tests unitaires simples avec assert."""

    # Test Euclidean_distance
    a = [1] * 10
    assert Euclidean_distance(a, a) == 0, "Erreur : distance nulle attendue"

    b = [0] * 10
    c = [1] * 10
    assert Euclidean_distance(b, c) == math.sqrt(10), "Erreur distance connue"

    # Test Euclidean_house
    refs = [
        {"house": "A", "answer": [0] * 10},
        {"house": "B", "answer": [10] * 10}
    ]
    answer = [1] * 10
    assert Euclidean_house(answer, refs) == "A", "Erreur Euclidean_house"

    # Test NN (taille de la liste)
    refs_nn = [
        {"house": "A", "answer": [i] * 10} for i in range(5)
    ]
    neighbors = NN([0] * 10, refs_nn, 3)
    assert len(neighbors) <= 3, "Erreur : NN retourne trop de voisins"

    # Test NN_house (majorité)
    neighbors_test = [
        {"house": "A", "answer": []},
        {"house": "A", "answer": []},
        {"house": "B", "answer": []}
    ]
    assert NN_house(neighbors_test) == "A", "Erreur NN_house majorité"

    # Test NN_repartition (taille du dictionnaire)
    dico = {
        "Alice": [1] * 10,
        "Bob": [2] * 10
    }
    refs_rep = [
        {"house": "A", "answer": [1] * 10},
        {"house": "B", "answer": [2] * 10}
    ]
    repartition = NN_repartition(dico, refs_rep, 1)
    assert len(repartition) == len(dico), "Erreur NN_repartition taille"

    print("Tous les tests unitaires ont réussi avec succès.")

if __name__ == "__main__":
    run_tests()