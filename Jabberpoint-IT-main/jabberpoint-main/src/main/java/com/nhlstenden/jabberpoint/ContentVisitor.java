package com.nhlstenden.jabberpoint;

/**
 * <p>The visitor interface for performing operations on various {@code Content} types.</p>
 * <p>This interface is part of the Visitor design pattern, allowing the separation of operations
 * from the object structure they operate on. Implementations can define specific behavior
 * for different content types, such as {@code Presentation}, {@code TextDocument}, or {@code Spreadsheet},
 * without relying on {@code instanceof} checks.</p>
 *
 * <p>Currently, this interface defines a visitor method for:
 * <ul>
 *   <li>{@code visitPresentation(Presentation presentation)}</li>
 * </ul>
 * Future content types can be supported by adding additional visit methods (see commented examples).</p>
 *
 * <p>This promotes open/closed design and encourages extensibility as new content types are introduced.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com,
 *         Gert Florijn, Sylvia Stuurman,
 *         Thu Tran, Bocheng Peng
 * @version 1.7 2025/04/02
 */
public interface ContentVisitor
{
    void visitPresentation(Presentation presentation);
}