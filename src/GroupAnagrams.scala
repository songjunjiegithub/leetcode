object GroupAnagrams {
    def groupAnagrams(strs: Array[String]): List[List[String]] = {
        val map = scala.collection.mutable.HashMap.empty[String, List[String]]
        var tmp = ""

        for (s <- strs) {
            tmp = s.sortBy(x => x)
            map.put(tmp, map.getOrElse(tmp, List()) :+ s)
        }

        map.values.toList
    }

    def main(args: Array[String]): Unit = {
//        输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
//        输出:
//        [
//        ["ate","eat","tea"],
//        ["nat","tan"],
//        ["bat"]
//        ]

        val strs = Array("eat", "tea", "tan", "ate", "nat", "bat")
        println(groupAnagrams(strs))


    }
}
