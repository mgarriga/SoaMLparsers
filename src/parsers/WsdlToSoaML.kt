package parsers

import edu.uncoma.fai.WsdlToSoaML.parser.WsdlToSoaML
import io.github.cdimascio.dotenv.dotenv
import schemas.Case
import schemas.getCaseCollection
import java.io.File


private val dotenv = dotenv { directory = "./" }


fun getCasesFromWsdl(path: String): MutableList<Case> {
    val cases = mutableListOf<Case>()

    File(path).walk().filter { it.extension == "wsdl2" }.forEach {
        val soaMLInterface = WsdlToSoaML.createSoaMLInterface(it.absolutePath)
        val case = Case(soaMLInterface, soaMLInterface.name)
        cases.add(case)
    }
    return cases
}


fun loadWsdl() {
    println("Processing WSDL")
    val path = dotenv["WSDL_PATH"]!!
    val insertOasInDB = dotenv["INSERT_WSDL_IN_DB"] == "true"
    if (insertOasInDB) {
        val caseCollection = getCaseCollection()
        val cases = getCasesFromWsdl(path)
        caseCollection.insertMany(cases)
        println("Insert succefull now there are ${caseCollection.count()} Cases in the KB")
    }
}


fun main(args: Array<String>) {
    val dropDB = dotenv["DROP_DB"] == "true"

    if (dropDB) {
        val caseCollection = getCaseCollection()
        caseCollection.drop()
    }
    loadWsdl()
}
