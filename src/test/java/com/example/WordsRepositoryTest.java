package com.example;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class WordsRepositoryTest {

    @Before
    public void setup() {
        WordsRepository.clearCount();
    }

    @Test
    public void testGetWordsFromPinyinIfNone() {
        List<String> result = WordsRepository.getWordsFromPinyin("noword");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetWordsFromJianpinReturnOne() {
        List<String> result = WordsRepository.getWordsFromPinyin("hwjs");
        List<String> expected = Arrays.asList("华为技术");
        assertThat(result, is(expected));
    }

    @Test
    public void testGetWordsFromQuanpinReturnOne() {
        List<String> result = WordsRepository.getWordsFromPinyin("huaweijishu");
        List<String> expected = Arrays.asList("华为技术");
        assertThat(result, is(expected));
    }

    @Test
    public void testGetWordsFromQuanpinReturnOneAnother() {
        List<String> result = WordsRepository.getWordsFromPinyin("chunfeng");
        List<String> expected = Arrays.asList("春风");
        assertThat(result, is(expected));
    }

    @Test
    public void testGetWordsFromJianpinReturnMany() {
        List<String> result = WordsRepository.getWordsFromPinyin("cf");
        List<String> expected = Arrays.asList("采访", "财富", "厨房", "春风", "赤峰");
        assertThat(result, is(expected));
    }

    @Test
    public void testGetWordsFromQuanpinReturnMany() {
        List<String> result = WordsRepository.getWordsFromPinyin("gaoxiao");
        List<String> expected = Arrays.asList("高校", "高效", "高小");
        assertThat(result, is(expected));
    }


    @Test
    public void testAddUseCount() {
        ZhWord zhWord = WordsRepository.getZhWordByName("厨房");
        assertThat(zhWord.getCount(), is(0));
        WordsRepository.addUseCount("厨房");
        assertThat(zhWord.getCount(), is(1));
        WordsRepository.addUseCount("厨房");
        assertThat(zhWord.getCount(), is(2));
    }

    @Test
    public void testGetWordsFromJianpinReturnManyForSort() {
        WordsRepository.addUseCount("厨房");
        WordsRepository.addUseCount("厨房");
        WordsRepository.addUseCount("厨房");
        WordsRepository.addUseCount("春风");
        WordsRepository.addUseCount("春风");
        WordsRepository.addUseCount("财富");
        List<String> result = WordsRepository.getWordsFromPinyin("cf");
        List<String> expected = Arrays.asList("厨房", "春风", "财富", "采访", "赤峰");
        assertThat(result, is(expected));
    }

    @Test
    public void testAddNewZhWord() {
        ZhWord zhWordNew = new ZhWord("祖国", "zg", "zuguo");
        WordsRepository.addZhWord(zhWordNew);
        ZhWord zhWordExpected = WordsRepository.getZhWordByName("祖国");
        assertThat(zhWordExpected, sameInstance(zhWordNew));
    }

    @Test
    public void testAddNewZhWordToOverride() {
        ZhWord zhWordNew = new ZhWord("南宁", "nn", "nanning");
        ZhWord zhWordExist = WordsRepository.getZhWordByName("南宁");
        assertThat(zhWordExist, not(sameInstance(zhWordNew)));
        WordsRepository.addZhWord(zhWordNew);
        ZhWord zhWordExistNew = WordsRepository.getZhWordByName("南宁");
        assertThat(zhWordExistNew, sameInstance(zhWordNew));
    }

}
