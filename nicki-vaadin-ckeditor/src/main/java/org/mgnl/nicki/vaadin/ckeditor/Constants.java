package org.mgnl.nicki.vaadin.ckeditor;
/*-
 * #%L
 * nicki-vaadin-ckeditor
 * %%
 * Copyright (C) 2020 - 2021 Ralf Hirning
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import org.jsoup.safety.Safelist;

import java.util.Locale;

public class Constants {

    /**
     * Configuration types
     */
    public enum ConfigType {

        alignment,
        autosave,
        balloonToolbar,
        blockToolbar,
        ckfinder,
        cloudServices,
        codeBlock,
        exportPdf,
        exportWord,
        extraPlugins,
        fontBackgroundColor,
        fontColor,
        fontFamily,
        fontSize,
        heading,
        highlight,
        image,
        indentBlock,
        initialData,
        language,
        link,
        mediaEmbed,
        mention,
        placeholder,
        plugins,
        removePlugins,
        restrictedEditing,
        simpleUpload,
        table,
        title,
        htmlEmbed,
        toolbar,
        typing,
        wordCount,
        wproofreader

    }

    /**
     * Type of editor, currently four types of editor are supported.
     * They are CLASSIC, BALLOON, INLINE, DECOUPLED.
     */
    public enum EditorType {

        CLASSIC,
        BALLOON,
        INLINE,
        DECOUPLED;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ENGLISH);
        }

    }

    /**
     * Language sets
     */
    public enum Language {

        af("af"),
        ar("ar"),
        ast("ast"),
        az("az"),
        bg("bg"),
        ca("ca"),
        cs("cs"),
        da("da"),
        de("de"),
        de_ch("de-ch"),
        el("el"),
        en_au("en-au"),
        en_gb("en-gb"),
        eo("eo"),
        es("es"),
        et("et"),
        eu("eu"),
        fa("fa"),
        fi("fi"),
        fr("fr"),
        gl("gl"),
        gu("gu"),
        hi("hi"),
        he("he"),
        hr("hr"),
        hu("hu"),
        id("id"),
        it("it"),
        ja("ja"),
        km("km"),
        kn("kn"),
        ko("ko"),
        ku("ku"),
        lt("lt"),
        lv("lv"),
        ms("ms"),
        nb("nb"),
        ne("ne"),
        nl("nl"),
        no("no"),
        oc("oc"),
        pl("pl"),
        pt("pt"),
        pt_br("pt-br"),
        ro("ro"),
        ru("ru"),
        si("si"),
        sk("sk"),
        sl("sl"),
        sq("sq"),
        sr("sr"),
        sr_latn("sr-latn"),
        sv("sv"),
        th("th"),
        tr("tr"),
        tt("tt"),
        ug("ug"),
        uk("uk"),
        vi("vi"),
        zh("zh"),
        zh_cn("zh-cn");

        private final String language;

        Language(String language) {
            this.language = language;
        }

        public String getLanguage(){
            return this.language;
        }

    }

    /**
     * Theme
     */
    public enum ThemeType {

        DARK,
        LIGHT;

        public String toString() {
            return name().toLowerCase(Locale.ENGLISH);
        }

    }

    /**
     * Toolbar that applied to editor. Items like headin, pipe, blockQuote and etc can be used.
     * Editor can have one or more toolbar items.
     */
    public enum Toolbar {

        heading("heading"),
        pipe("|"),
        blockQuote("blockQuote"),
        bold("bold"),
        italic("italic"),
        ckfinder("ckfinder"),
        imageUpload("imageUpload"),
        indent("indent"),
        outdent("outdent"),
        link("link"),
        numberedList("numberedList"),
        bulletedList("bulletedList"),
        mediaEmbed("mediaEmbed"),
        undo("undo"),
        redo("redo"),
        underline("underline"),
        strikethrough("strikethrough"),
        code("code"),
        subscript("subscript"),
        superscript("superscript"),
        selectAll("selectAll"),
        removeFormat("removeFormat"),
        horizontalLine("horizontalLine"),
        pageBreak("pageBreak"),
        specialCharacters("specialCharacters"),
        alignment("alignment"),
        codeBlock("codeBlock"),
        highlight("highlight"),
        htmlEmbed("htmlEmbed"),
        fontSize("fontSize"),
        fontFamily("fontFamily"),
        fontColor("fontColor"),
        fontBackgroundColor("fontBackgroundColor"),
        restrictedEditing("restrictedEditing"),
        restrictedEditingException("restrictedEditingException"),
        todoList("todoList"),
        exportPdf("exportPdf"),
        exportWord("exportWord"),
        insertTable("insertTable");

        private final String value;

        Toolbar(String value) {
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }

    }

    /**
     * All available plugins
     */
    public enum Plugins {

        Essentials,
        UploadAdapter,
        Autoformat,
        Autosave,
        Alignment,
        Bold,
        Italic,
        Underline,
        Strikethrough,
        Code,
        Subscript,
        Superscript,
        BlockQuote,
        CKFinder,
        Clipboard,
        CodeBlock,
        EasyImage,
        Base64UploadAdapter,
        Heading,
        Font,
        Highlight,
        HtmlEmbed,
        HorizontalLine,
        Image,
        ImageCaption,
        ImageStyle,
        ImageToolbar,
        ImageUpload,
        ImageResize,
        Indent,
        Link,
        List,
        Markdown,
        MediaEmbed,
        Mention,
        Paragraph,
        PasteFromOffice,
        PageBreak,
        PendingActions,
        RemoveFormat,
        RemoveFormatLinks,
        RestrictedEditingMode,
        SelectAll,
        StandardEditingMode,
        SpecialCharacters,
        SpecialCharactersEssentials,
        TodoList,
        Table,
        TableToolbar,
        TableProperties,
        TableCellProperties,
        TextTransformation,
        ExportPdf,
        ExportWord,
        WProofreader,
        WordCount

    }

    /**
     * Sanitize type
     */
    public enum SanitizeType {

        none(Safelist.none()),
        simpleText(Safelist.simpleText()),
        basic(Safelist.basic()),
        basicWithImages(Safelist.basicWithImages()),
        relaxed(Safelist.relaxed());

        private final Safelist safelist;

        SanitizeType(Safelist value) {
            this.safelist = value;
        }

        public Safelist getValue(){
            return this.safelist;
        }

    }

}
