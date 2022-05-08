package cn.lsr.noveladmin.constants;

public class BookIndexTemplate {
    public static final String BookIndex = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"workDirection\": {\n" +
            "        \"type\": \"boolean\"\n" +
            "      },\n" +
            "      \"picUrl\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"bookName\": {\n" +
            "        \"type\": \"text\", \n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"search_analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"authorId\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"authorName\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"search_analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"bookDesc\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"score\": {\n" +
            "        \"type\": \"float\"\n" +
            "      },\n" +
            "      \"bookStatus\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"visitCount\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"wordCount\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"commentCount\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"lastIndexUpdateTime\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"isVip\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"status\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"createTime\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"isRecommend\": {\n" +
            "        \"type\": \"boolean\"\n" +
            "      },\n" +
            "      \"typeName\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"all\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
